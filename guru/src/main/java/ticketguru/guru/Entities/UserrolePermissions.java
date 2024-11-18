package ticketguru.guru.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserrolePermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long permissionId;
    private String permissionDescription;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userrolePermissions") //one-to-many relationship with Userrole
    private List<Userrole> Userroles;

    public UserrolePermissions(Long permissionId, String permissionDescription) {
        this.permissionId = permissionId;
        this.permissionDescription = permissionDescription;
    }

    public UserrolePermissions() {
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public List<Userrole> getUserroles() {
        return Userroles;
    }

    public void setUserroles(List<Userrole> userroles) {
        Userroles = userroles;
    }

    @Override
    public String toString() {
        return "UserrolePermissions [permissionId=" + permissionId + ", permissionDescription=" + permissionDescription
                + "]";
    }
}
