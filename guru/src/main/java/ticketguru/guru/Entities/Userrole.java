package ticketguru.guru.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Userrole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userroleId;
    private String role;

    @ManyToOne
    @JoinColumn(name = "permissionId") //many-to-one relationship with UserrolePermissions
    private UserrolePermissions userrolePermissions;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userrole") //one-to-many relationship with TGUser
    private List<TGUser> users;

    public Userrole(Long userroleId, String role) {
        this.userroleId = userroleId;
        this.role = role;
    }  
    
    public Userrole() {
    } 

    public Long getUserroleId() {
        return userroleId;
    }

    public void setUserroleId(Long userroleId) {
        this.userroleId = userroleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsers(List<TGUser> users) {
         this.users = users;
    }
    

    @Override
    public String toString() {
        return "Userrole [userroleId=" + userroleId + ", role=" + role + "]";
    }
    
}
