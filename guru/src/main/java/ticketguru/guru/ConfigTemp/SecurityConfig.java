package ticketguru.guru.ConfigTemp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService; // Constructor injection to avoid circular dependency
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN") // Allow API access to both roles
                .requestMatchers("/admin/**").hasRole("ADMIN") // Require ADMIN role for admin paths
                .anyRequest().authenticated() // Require authentication for any other request
            )
            .httpBasic() // Enable basic HTTP authentication
            .and()
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity, enable if needed
            .headers(headers -> headers.frameOptions().disable()); // Enable H2 console frames

        return http.build();
    }
}
