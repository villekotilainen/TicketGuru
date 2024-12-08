package ticketguru.guru.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ticketguru.guru.Services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomCorsConfig customCorsConfig;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomCorsConfig customCorsConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.customCorsConfig = customCorsConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(customCorsConfig.getCorsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Disabled CSRF for now; enable it for production
                .authorizeHttpRequests(authorize -> authorize

                        // Allow public access to static resources
                        .requestMatchers("/styles.css", "/css/**", "/js/**", "/images/**").permitAll()

                        // Allow public access to the login page
                        .requestMatchers("/").permitAll()

                        // Public access endpoints
                        .requestMatchers(HttpMethod.GET, "/buy").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/tickettypes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tickets/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/transactions/**").permitAll()

                        // Authenticated users
                        .requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()

                        // Static resources and H2-console
                        .requestMatchers("/css/**", "/h2-console/**", "/").permitAll()

                        // Role-based access control
                        .requestMatchers(HttpMethod.POST, "/api/events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/events/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/events/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/tickets/*").hasAnyRole("ADMIN", "SALESPERSON")
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tickets/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/tickettypes/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tickettypes/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tickettypes/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/transactions/*").hasAnyRole("ADMIN", "SALESPERSON")
                        .requestMatchers(HttpMethod.PUT, "/api/transactions/*").hasAnyRole("ADMIN", "SALESPERSON")
                        .requestMatchers(HttpMethod.DELETE, "/api/transactions/*").hasRole("ADMIN")

                        .requestMatchers("/api/venues/*").hasRole("ADMIN")
                        .requestMatchers("/api/users/*").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/") // Specify your custom login page
                        .loginProcessingUrl("/login") // Endpoint to process login form submissions
                        .defaultSuccessUrl("/myynticlient", true) // Redirect after successful login
                        .permitAll() // Allow everyone to access the login page
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Logout endpoint
                        .logoutSuccessUrl("/") // Redirect after logout
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // For H2-console

        return http.build();
    }
}
