package App.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import App.Services.RoleService;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoleService roleService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = extractToken(request);
        if (token != null && jwtUtil.validateToken(token))
        {
            try
            {
                String email = jwtUtil.extractEmail(token);
                String role = roleService.getRole(email); // Fetch role from DB

                if (role != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(email, null, 
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))); // FIX: Add ROLE_ prefix
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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
}
