package RepositoryTests;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import ticketguru.guru.GuruApplication;
import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Repositories.TGUserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Sets up an in-memory database for testing repositories
@ContextConfiguration(classes = GuruApplication.class)
class TGUserRepositoryTest {

    @Autowired
    private TGUserRepository tgUserRepository;

    @BeforeEach
    void setup() {
        // Arrange: Create and save test users
        TGUser user1 = new TGUser(null, "john.doe@example.com", "John", "Doe", null, null, null, null);
        TGUser user2 = new TGUser(null, "jane.doe@example.com", "Jane", "Doe", null, null, null, null);

        tgUserRepository.save(user1);
        tgUserRepository.save(user2);
    }

    @Test
    void shouldFindUserByEmail() {
        // Act
        TGUser user = tgUserRepository.findByEmail("john.doe@example.com");

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getFirstName()).isEqualTo("John");
    }

    @Test
    void shouldFindUserByFirstNameAndLastName() {
        // Act
        TGUser user = tgUserRepository.findByFirstNameAndLastName("Jane", "Doe");

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(user.getFirstName()).isEqualTo("Jane");
    }

    @Test
    void shouldReturnNullWhenEmailNotFound() {
        // Act
        TGUser user = tgUserRepository.findByEmail("not.found@example.com");

        // Assert
        assertThat(user).isNull();
    }

    @Test
    void shouldReturnNullWhenFirstNameAndLastNameNotFound() {
        // Act
        TGUser user = tgUserRepository.findByFirstNameAndLastName("Non", "Existent");

        // Assert
        assertThat(user).isNull();
    }
}
