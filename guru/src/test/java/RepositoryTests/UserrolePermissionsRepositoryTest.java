package RepositoryTests;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import ticketguru.guru.GuruApplication;
import ticketguru.guru.Entities.UserrolePermissions;
import ticketguru.guru.Repositories.UserrolePermissionsRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = GuruApplication.class)
public class UserrolePermissionsRepositoryTest {

    @Autowired
    private UserrolePermissionsRepository userrolePermissionsRepository;

    @BeforeEach
    void setUp() {
        // Set up sample data
        UserrolePermissions permission1 = new UserrolePermissions(null, null);
        permission1.setPermissionDescription("ADMIN_ACCESS");

        UserrolePermissions permission2 = new UserrolePermissions(null, null);
        permission2.setPermissionDescription("USER_ACCESS");

        userrolePermissionsRepository.save(permission1);
        userrolePermissionsRepository.save(permission2);
    }

    @Test
    void findByPermissionDescription_shouldReturnCorrectPermission() {
        // Act
        UserrolePermissions result = userrolePermissionsRepository.findByPermissionDescription("ADMIN_ACCESS");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPermissionDescription()).isEqualTo("ADMIN_ACCESS");
    }

    @Test
    void findByPermissionDescription_shouldReturnNullForNonExistentPermission() {
        // Act
        UserrolePermissions result = userrolePermissionsRepository.findByPermissionDescription("NON_EXISTENT");

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void repository_shouldSaveAndRetrievePermissions() {
        // Arrange
        UserrolePermissions newPermission = new UserrolePermissions(null, null);
        newPermission.setPermissionDescription("MODERATOR_ACCESS");
        userrolePermissionsRepository.save(newPermission);

        // Act
        UserrolePermissions retrievedPermission = userrolePermissionsRepository.findByPermissionDescription("MODERATOR_ACCESS");

        // Assert
        assertThat(retrievedPermission).isNotNull();
        assertThat(retrievedPermission.getPermissionDescription()).isEqualTo("MODERATOR_ACCESS");
    }
}
