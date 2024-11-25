package ticketguru.guru.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Repositories.TGUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TGUserRepository tgUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TGUser tgUser = tgUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(tgUser.getEmail())
                .password(tgUser.getPassword())
                .roles(tgUser.getUserrole().getRole())
                .build();
    }
}