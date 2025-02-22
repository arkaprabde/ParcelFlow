package App.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter
{
	//private final Dotenv dotenv = Dotenv.load();
	//private final String SECRET_KEY = dotenv.get("SECRET_KEY");

	@Autowired
	private JwtUtil jwtUtil;
    
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = extractToken(request), roles = "", username = "";
        if(token != null && validateToken(token))
        {
			try
			{
				username = extractUsername(token);
				roles = extractRoles(token);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
				if(jwtUtil.validateToken(token, ))
            UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority(roles)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request)
    {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            return header.substring(7);
        return null;
    }

	private boolean validateToken(String token)
	{
        return jwtUtil.validateToken(token);
    }

    private String extractUsername(String token) throws Exception
    {
        Claims claims = jwtUtil.getClaims(token);
        return claims.getSubject();
    }

    private String extractRoles(String token) throws Exception
    {
        Claims claims = jwtUtil.getClaims(token);
        return claims.get("roles", String.class);
    }
}