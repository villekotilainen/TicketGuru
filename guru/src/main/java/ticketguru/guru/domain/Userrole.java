package ticketguru.guru.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Userrole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userroleId;
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userrole") //one-to-many relationship with User
    private List<User> users;

    public Userrole(Long userroleId, String role) {
        this.userroleId = userroleId;
        this.role = role;
    }  
    
    public Userrole() {
        this.userroleId = null;
        this.role = null;
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

    public void setUserId(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Userrole [userroleId=" + userroleId + ", role=" + role + "]";
    }
    
}
