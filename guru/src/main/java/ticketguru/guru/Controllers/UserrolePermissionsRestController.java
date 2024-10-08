package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ticketguru.guru.Entities.UserrolePermissions;
import ticketguru.guru.Repositories.UserrolePermissionsRepository;

@RestController
@RequestMapping("/api/permissions")
public class UserrolePermissionsRestController {

    @Autowired
    private UserrolePermissionsRepository userrolePermissionsRepository;

    // Get all permissions
    @GetMapping
    public List<UserrolePermissions> getAllPermissions() {
        return userrolePermissionsRepository.findAll();
    }

    // Get a specific permission by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserrolePermissions> getPermissionById(@PathVariable("id") Long id) {
        Optional<UserrolePermissions> permission = userrolePermissionsRepository.findById(id);
        if (permission.isPresent()) {
            return new ResponseEntity<>(permission.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new permission
    @PostMapping
    public ResponseEntity<UserrolePermissions> createPermission(@RequestBody UserrolePermissions permission) {
        try {
            UserrolePermissions newPermission = userrolePermissionsRepository.save(permission);
            return new ResponseEntity<>(newPermission, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing permission
    @PutMapping("/{id}")
    public ResponseEntity<UserrolePermissions> updatePermission(@PathVariable("id") Long id, @RequestBody UserrolePermissions updatedPermission) {
        Optional<UserrolePermissions> existingPermission = userrolePermissionsRepository.findById(id);
        if (existingPermission.isPresent()) {
            UserrolePermissions permission = existingPermission.get();
            permission.setPermissionDescription(updatedPermission.getPermissionDescription());
            userrolePermissionsRepository.save(permission);
            return new ResponseEntity<>(permission, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a permission
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePermission(@PathVariable("id") Long id) {
        try {
            userrolePermissionsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
