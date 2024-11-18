package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserroleTest {
    private Userrole userrole;
    private UserrolePermissions mockPermissions;

    @BeforeEach
    void setUp() {
        mockPermissions = mock(UserrolePermissions.class);
        userrole = new Userrole(1L, "ADMIN");
        userrole.setUserrolePermissions(mockPermissions);
    }

    @Test
    void testGettersAndSetters() {
        // Test initial values
        assertEquals(1L, userrole.getUserroleId());
        assertEquals("ADMIN", userrole.getRole());
        assertEquals(mockPermissions, userrole.getUserrolePermissions());

        // Test setters
        userrole.setUserroleId(2L);
        userrole.setRole("USER");
        UserrolePermissions newMockPermissions = mock(UserrolePermissions.class);
        userrole.setUserrolePermissions(newMockPermissions);

        assertEquals(2L, userrole.getUserroleId());
        assertEquals("USER", userrole.getRole());
        assertEquals(newMockPermissions, userrole.getUserrolePermissions());
    }

    @Test
    void testDefaultConstructor() {
        Userrole defaultUserrole = new Userrole();

        assertNull(defaultUserrole.getUserroleId());
        assertNull(defaultUserrole.getRole());
        assertNull(defaultUserrole.getUserrolePermissions());
    }

    @Test
    void testConstructorWithParameters() {
        Userrole newUserrole = new Userrole(3L, "MODERATOR");

        assertEquals(3L, newUserrole.getUserroleId());
        assertEquals("MODERATOR", newUserrole.getRole());
        assertNull(newUserrole.getUserrolePermissions());
    }

    @Test
    void testSetUsers() {
        TGUser mockUser1 = mock(TGUser.class);
        TGUser mockUser2 = mock(TGUser.class);

        userrole.setUsers(List.of(mockUser1, mockUser2));
        List<TGUser> users = userrole.getUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(mockUser1, users.get(0));
        assertEquals(mockUser2, users.get(1));
    }

    @Test
    void testToString() {
        String toStringOutput = userrole.toString();

        assertTrue(toStringOutput.contains("userroleId=1"));
        assertTrue(toStringOutput.contains("role=ADMIN"));
    }
}
