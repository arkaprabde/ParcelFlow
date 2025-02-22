package App.Security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil
{
    private static final Dotenv dotenv = Dotenv.load();
    private static final String SECRET_KEY = dotenv.get("JWT_SECRET");
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    private static final Key SIGNING_KEY = generateSigningKey();
    
    private static Key generateSigningKey()
    {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
    }

    public String generateToken(String email)
    {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String token)
    {
        try
        {
            return !isTokenExpired(getClaims(token)) && ;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public String extractEmail(String token) throws Exception
    {
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(Claims claims)
    {
        return claims.getExpiration().before(new Date());
    }

    public Claims getClaims(String token) throws Exception
    {
        return Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token).getBody();
    }
}