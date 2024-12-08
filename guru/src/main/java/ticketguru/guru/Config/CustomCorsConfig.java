package ticketguru.guru.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CustomCorsConfig {

    @Bean
    public CorsConfigurationSource getCorsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "https://ticket-guru-visionaarit-ticketguru2.2.rahtiapp.fi",
            "http://localhost:8080"
        )); // Specify allowed origin(s)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "OPTIONS", "DELETE")); // Include DELETE if required
        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Limit to required headers
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization", "Content-Type")); // Expose only if required

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply to all paths, adjust if needed

        return source;
    }
}

