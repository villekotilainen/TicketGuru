package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Entities.Userrole;
import ticketguru.guru.Repositories.TGUserRepository;
import ticketguru.guru.Repositories.UserroleRepository;
import ticketguru.guru.dto.Userrequest;

@RestController
@RequestMapping("/api/users")
public class TGUserRestController {

    @Autowired
    private TGUserRepository tgUserRepository;

    @Autowired
    private UserroleRepository userroleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<TGUser> getAllUsers() {
        return tgUserRepository.findAll();
    }

    // GET: Hae yksittäinen käyttäjä ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<TGUser> getUserById(@PathVariable Long id) {
        Optional<TGUser> tguser = tgUserRepository.findById(id);
        return tguser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Lisää uusi käyttäjä
    @PostMapping
    public ResponseEntity<TGUser> createTgUser(@RequestBody TGUser newTgUser) {
        TGUser savedTgUser = tgUserRepository.save(newTgUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTgUser);
    }

    // PUT: Päivitä olemassa oleva tapahtuma
    @PutMapping("/{id}")
    public ResponseEntity<TGUser> updateTgUser(@PathVariable Long id, @RequestBody TGUser tgUserDetails) {
        Optional<TGUser> optionalTgUser = tgUserRepository.findById(id);

        if (optionalTgUser.isPresent()) {
            TGUser existingTgUser = optionalTgUser.get();
            existingTgUser.setEmail(tgUserDetails.getEmail());
            existingTgUser.setFirstName(tgUserDetails.getFirstName());
            existingTgUser.setLastName(tgUserDetails.getLastName());
            existingTgUser.setPassword(tgUserDetails.getPassword());
            existingTgUser.setAddress(tgUserDetails.getAddress());
            existingTgUser.setPhone(tgUserDetails.getPhone());

            TGUser updatedTgUser = tgUserRepository.save(existingTgUser);
            return ResponseEntity.ok(updatedTgUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Poista tapahtuma ID:n perusteella
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTgUser(@PathVariable Long id) {
        if (tgUserRepository.existsById(id)) {
            tgUserRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/me")
    public TGUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the email/username of the logged-in user
        return tgUserRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Userrequest userRequest) {
        try {
            // Validate role
            String role = userRequest.getRole();
            Userrole userrole = userroleRepository.findByRole(role)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + role));
    
            // Create and save new TGUser
            TGUser newUser = new TGUser(
                null,
                userRequest.getEmail(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                passwordEncoder.encode(userRequest.getPassword()), // Encode password
                userRequest.getAddress(),
                userRequest.getPhone(),
                userrole
            );
            TGUser savedUser = tgUserRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed.");
        }
    }
}
