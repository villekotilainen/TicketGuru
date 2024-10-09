package ticketguru.guru.Entities;

import java.time.LocalDateTime;
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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private LocalDateTime transactionDate;
    private Double totalSum;
    private Boolean succeeded;

    @ManyToOne
    @JoinColumn(name = "userId") //many-to-one relationship with User
    private TGUser user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction") //one-to-many realationship with Ticket
    private List<Ticket> tickets;

    public Transaction(Long transactionId, LocalDateTime transactionDate, Double totalSum, Boolean succeeded, TGUser user) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.totalSum = totalSum;
        this.succeeded = succeeded;
        this.user = user;
    }

    public Transaction() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    public Boolean getSucceeded() {
        return succeeded;
    }

    public void setSucceeded(Boolean succeeded) {
        this.succeeded = succeeded;
    }

    public TGUser getUser() {
        return user;
    }

    public void setUser(TGUser user) {
        this.user = user;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Transaction [transactionId=" + transactionId + ", transactionDate=" + transactionDate + ", totalSum="
                + totalSum + ", succeeded=" + succeeded + ", user=" + user + "]";
    } 
}
