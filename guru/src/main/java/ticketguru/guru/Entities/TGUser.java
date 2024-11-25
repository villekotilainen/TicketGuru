package ticketguru.guru.Entities;

import org.springframework.security.core.userdetails.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TGUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;
    
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "userroleId") //many-to-one relationship with Userrole
    private Userrole userrole;

    public TGUser(Long userId, String email, String firstName, String lastName, String password, String address, String phone, Userrole userrole) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.userrole = userrole;
    }

    // User ilman roolia testailua varten!
    public TGUser(Long userId, String email, String firstName, String lastName, String password, String address, String phone) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public TGUser() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Userrole getUserrole() {
        return userrole;
    }

    public void setUserrole(Userrole userrole) {
        this.userrole = userrole;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
                + ", password=" + password + ", address=" + address + ", phone=" + phone + ", userrole="
                + userrole + "]";
    }

    public User orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    } 
}
