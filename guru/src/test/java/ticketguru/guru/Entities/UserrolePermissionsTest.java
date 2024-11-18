package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserrolePermissionsTest {
    private UserrolePermissions userrolePermissions;
    private List<Userrole> userroles;

    @BeforeEach
    void setUp() {
        // Initialize test objects
        userrolePermissions = new UserrolePermissions(1L, "Admin permissions");
        userroles = new ArrayList<>();

        // Add mock Userroles
        Userrole userrole1 = new Userrole(1L, "Admin");
        Userrole userrole2 = new Userrole(2L, "Editor");
        userroles.add(userrole1);
        userroles.add(userrole2);
    }

    @Test
    void testGetPermissionId() {
        assertEquals(1L, userrolePermissions.getPermissionId());
    }

    @Test
    void testSetPermissionId() {
        userrolePermissions.setPermissionId(2L);
        assertEquals(2L, userrolePermissions.getPermissionId());
    }

    @Test
    void testGetPermissionDescription() {
        assertEquals("Admin permissions", userrolePermissions.getPermissionDescription());
    }

    @Test
    void testSetPermissionDescription() {
        userrolePermissions.setPermissionDescription("Editor permissions");
        assertEquals("Editor permissions", userrolePermissions.getPermissionDescription());
    }

    @Test
    void testGetUserroles() {
        userrolePermissions.setUserroles(userroles);
        assertEquals(2, userrolePermissions.getUserroles().size());
    }

    @Test
    void testSetUserroles() {
        userrolePermissions.setUserroles(userroles);
        List<Userrole> retrievedUserroles = userrolePermissions.getUserroles();
        assertNotNull(retrievedUserroles);
        assertEquals(2, retrievedUserroles.size());
        assertEquals("Admin", retrievedUserroles.get(0).getRole());
        assertEquals("Editor", retrievedUserroles.get(1).getRole());
    }

    @Test
    void testToString() {
        String expected = "UserrolePermissions [permissionId=1, permissionDescription=Admin permissions]";
        assertEquals(expected, userrolePermissions.toString());
    }

    @Test
    void testEmptyConstructor() {
        UserrolePermissions emptyUserrolePermissions = new UserrolePermissions();
        assertNull(emptyUserrolePermissions.getPermissionId());
        assertNull(emptyUserrolePermissions.getPermissionDescription());
        assertNull(emptyUserrolePermissions.getUserroles());
    }
}
