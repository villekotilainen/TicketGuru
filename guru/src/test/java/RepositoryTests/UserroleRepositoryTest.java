package RepositoryTests;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import ticketguru.guru.GuruApplication;
import ticketguru.guru.Entities.Userrole;
import ticketguru.guru.Repositories.UserroleRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = GuruApplication.class)
public class UserroleRepositoryTest {

    @Autowired
    private UserroleRepository userroleRepository;

    @BeforeEach
    void setUp() {
        // Set up sample data
        Userrole roleAdmin = new Userrole();
        roleAdmin.setRole("ADMIN");

        Userrole roleUser = new Userrole();
        roleUser.setRole("USER");

        userroleRepository.save(roleAdmin);
        userroleRepository.save(roleUser);
    }

    @Test
    void findByRole_shouldReturnCorrectRole() {
        // Act
        Userrole result = userroleRepository.findByRole("ADMIN");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRole()).isEqualTo("ADMIN");
    }

    @Test
    void findByRole_shouldReturnNullForNonExistentRole() {
        // Act
        Userrole result = userroleRepository.findByRole("NON_EXISTENT_ROLE");

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void repository_shouldSaveAndRetrieveRoles() {
        // Arrange
        Userrole newRole = new Userrole();
        newRole.setRole("MODERATOR");
        userroleRepository.save(newRole);

        // Act
        Userrole retrievedRole = userroleRepository.findByRole("MODERATOR");

        // Assert
        assertThat(retrievedRole).isNotNull();
        assertThat(retrievedRole.getRole()).isEqualTo("MODERATOR");
    }
}
