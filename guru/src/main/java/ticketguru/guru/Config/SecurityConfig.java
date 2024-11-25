package ticketguru.guru.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

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
       public SecurityFilterChain configure(HttpSecurity http) throws Exception {
           http
                    .cors(cors -> cors.configurationSource(customCorsConfig.getCorsConfigurationSource()))
                    .csrf(csrf -> csrf.disable()) // disabled csrf for now, enable later for production?
                    .authorizeHttpRequests(
                            authorize -> authorize

                                    // Pyynnöt voi suorittaa ilman kirjautumatta
                                    .requestMatchers(HttpMethod.GET, "/buy").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/tickettypes/**").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/tickets/**").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/transactions/**").permitAll()

                                    // Allow access to /api/users/me for authenticated users
                                    .requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()
                                    
                                    .requestMatchers(antMatcher("/css/**")).permitAll()
                                    .requestMatchers(antMatcher("/h2-console/**")).permitAll()

                                    // Lipun tarkistus
                                    .requestMatchers(("/")).hasAnyRole("ADMIN", "SALESPERSON")
        
                                    // Event: vain admin voi muokata eventtejä
                                    .requestMatchers(HttpMethod.POST, "/api/events").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/api/events/*").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/api/events/*").hasRole("ADMIN")

                                    // Ticket: vain admin voi muokata lippuja, salesperson voi katsoa
                                    .requestMatchers(HttpMethod.GET, "/api/tickets/*").hasAnyRole("ADMIN", "SALESPERSON")
                                    .requestMatchers(HttpMethod.PUT, "/api/tickets/*").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/api/tickets/*").hasRole("ADMIN")

                                    // TGUser: vain admin 
                                    .requestMatchers("/api/users/*").hasRole("ADMIN")

                                    // Transaction: vain admin voi poistaa myyntitapahtuman
                                    .requestMatchers(HttpMethod.GET, "/api/transactions/*").hasAnyRole("ADMIN", "SALESPERSON")
                                    .requestMatchers(HttpMethod.PUT, "/api/transactions/*").hasAnyRole("ADMIN", "SALESPERSON")
                                    .requestMatchers(HttpMethod.DELETE, "/api/transactions/*").hasRole("ADMIN")

                                    // TicketType: vain admin voi muokata
                                    .requestMatchers(HttpMethod.POST, "/api/tickettypes/*").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/api/tickettypes/*").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/tickettypes/*").hasRole("ADMIN")

                                    // Venue: vain admin
                                    .requestMatchers("/api/venues/*").hasRole("ADMIN")

                                    // Protect TGUser endpoints for admin role
                                    .requestMatchers("/api/users/*").hasRole("ADMIN")

                                    .anyRequest().authenticated()
                    )  
                     .httpBasic(Customizer.withDefaults())
                    .headers(
                            headers -> headers
                                    .frameOptions(frameOptions -> frameOptions
                                    .disable())
                    )
                    .formLogin(formlogin -> 
                        formlogin
                        //.loginPage("/login")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler()) // redirect to page the user tried to access before login
                        .permitAll())
                    .logout(logout -> logout.permitAll());

            return http.build();
   
        }
   
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
   
        }
}