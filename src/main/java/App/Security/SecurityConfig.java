package App.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import App.Security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	@Autowired
	JwtFilter jwtFilter;
		
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/buyers/**").permitAll()
                .requestMatchers("/vendors/**").permitAll()
                .requestMatchers("/delivery/**").permitAll()
                .requestMatchers("/admin/**").permitAll()
                /*.requestMatchers("/buyers/**").hasRole("BUYER")
                .requestMatchers("/vendors/**").hasRole("VENDOR")
                .requestMatchers("/delivery/**").hasRole("DELIVERY_BOY")
                .requestMatchers("/admin/**").permitAll()*/
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
