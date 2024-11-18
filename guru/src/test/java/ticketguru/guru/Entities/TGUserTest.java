package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TGUserTest {

    private TGUser tgUser;
    private Userrole mockUserrole;

    @BeforeEach
    void setUp() {
        mockUserrole = mock(Userrole.class);
        tgUser = new TGUser(
                1L,
                "test@example.com",
                "John",
                "Doe",
                "securepassword",
                "123 Main St",
                "1234567890",
                mockUserrole
        );
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, tgUser.getUserId());
        assertEquals("test@example.com", tgUser.getEmail());
        assertEquals("John", tgUser.getFirstName());
        assertEquals("Doe", tgUser.getLastName());
        assertEquals("securepassword", tgUser.getPassword());
        assertEquals("123 Main St", tgUser.getAddress());
        assertEquals("1234567890", tgUser.getPhone());
        assertEquals(mockUserrole, tgUser.getUserrole());

        // Test setters
        tgUser.setUserId(2L);
        tgUser.setEmail("updated@example.com");
        tgUser.setFirstName("Jane");
        tgUser.setLastName("Smith");
        tgUser.setPassword("newpassword");
        tgUser.setAddress("456 Elm St");
        tgUser.setPhone("0987654321");
        Userrole newMockUserrole = mock(Userrole.class);
        tgUser.setUserrole(newMockUserrole);

        assertEquals(2L, tgUser.getUserId());
        assertEquals("updated@example.com", tgUser.getEmail());
        assertEquals("Jane", tgUser.getFirstName());
        assertEquals("Smith", tgUser.getLastName());
        assertEquals("newpassword", tgUser.getPassword());
        assertEquals("456 Elm St", tgUser.getAddress());
        assertEquals("0987654321", tgUser.getPhone());
        assertEquals(newMockUserrole, tgUser.getUserrole());
    }

    @Test
    void testUserWithoutRoleConstructor() {
        TGUser userWithoutRole = new TGUser(
                2L,
                "noRole@example.com",
                "Alice",
                "Brown",
                "rolelesspassword",
                "789 Maple Ave",
                "5551234567"
        );

        assertEquals(2L, userWithoutRole.getUserId());
        assertEquals("noRole@example.com", userWithoutRole.getEmail());
        assertEquals("Alice", userWithoutRole.getFirstName());
        assertEquals("Brown", userWithoutRole.getLastName());
        assertEquals("rolelesspassword", userWithoutRole.getPassword());
        assertEquals("789 Maple Ave", userWithoutRole.getAddress());
        assertEquals("5551234567", userWithoutRole.getPhone());
        assertNull(userWithoutRole.getUserrole());
    }

    @Test
    void testDefaultConstructor() {
        TGUser defaultUser = new TGUser();

        assertNull(defaultUser.getUserId());
        assertNull(defaultUser.getEmail());
        assertNull(defaultUser.getFirstName());
        assertNull(defaultUser.getLastName());
        assertNull(defaultUser.getPassword());
        assertNull(defaultUser.getAddress());
        assertNull(defaultUser.getPhone());
        assertNull(defaultUser.getUserrole());
    }

    @Test
    void testToString() {
        String toStringOutput = tgUser.toString();
        assertTrue(toStringOutput.contains("John"));
        assertTrue(toStringOutput.contains("Doe"));
        assertTrue(toStringOutput.contains("test@example.com"));
        assertTrue(toStringOutput.contains("123 Main St"));
    }
}
