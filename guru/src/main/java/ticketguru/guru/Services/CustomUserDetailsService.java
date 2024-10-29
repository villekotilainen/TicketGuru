package ticketguru.guru.Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();

    @Autowired
    public CustomUserDetailsService() {
        // Add some users for demonstration purposes
        users.put("user", org.springframework.security.core.userdetails.User.withUsername("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .authorities("ROLE_USER")
                .build());
        
        users.put("admin", org.springframework.security.core.userdetails.User.withUsername("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .authorities("ROLE_ADMIN")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }
}