package ticketguru.guru.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/","/api/**").permitAll() // Allow all requests to /api/**
                .requestMatchers("/events/**").hasAuthority("ADMIN") // Require ADMIN authority for events
                .requestMatchers("/venues/**").hasAuthority("ADMIN") // Require ADMIN authority for venues
                .requestMatchers("/users/**").hasAuthority("ADMIN") // Require ADMIN authority for users
                .requestMatchers("/userroles/**").hasAuthority("ADMIN") // Require ADMIN authority for user roles
                .requestMatchers("/transactions/**").hasAuthority("ADMIN") // Require ADMIN authority for transactions
                .requestMatchers("/tickets/**").hasAuthority("ADMIN") // Require ADMIN authority for tickets
                .anyRequest().authenticated() // Require authentication for any other request
            )
            .httpBasic()
            ;

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}