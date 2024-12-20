package ticketguru.guru.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketId;

    @NotNull(message = "Hashcode is required")
    @Size(min = 1, max = 255, message = "Hashcode must be between 1 and 255 characters")
    private String hashcode;

    @PastOrPresent(message = "Ticket used date cannot be in the future")
    private LocalDateTime ticketUsedDate;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionId", nullable = false)
    @JsonIgnoreProperties({ "tickets" })
    private Transaction transaction;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ticketTypeId", nullable = false)
    @JsonIgnoreProperties({ "tickets" })
    private TicketType ticketType;

    private boolean used = false;

    public Ticket(Long ticketId, String hashcode, LocalDateTime ticketUsedDate, TicketType ticketType,
            Transaction transaction) {
        this.ticketId = ticketId;
        this.hashcode = hashcode;
        this.ticketUsedDate = ticketUsedDate;
        this.ticketType = ticketType;
        this.transaction = transaction;
    }

    public Ticket(Long ticketId, String hashcode, LocalDateTime ticketUsedDate) {
        this.ticketId = ticketId;
        this.hashcode = hashcode;
        this.ticketUsedDate = ticketUsedDate;
    }

    public Ticket() {
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public LocalDateTime getTicketUsedDate() {
        return ticketUsedDate;
    }

    public void setTicketUsedDate(LocalDateTime ticketUsedDate) {
        this.ticketUsedDate = ticketUsedDate;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "Ticket [ticketId=" + ticketId + ", hashcode=" + hashcode + ", ticketUsedDate="
                + ticketUsedDate + ", ticketType=" + ticketType + ", used=" + used + "]";
    }
}
