package ticketguru.guru.Entities;

import jakarta.persistence.Entity;
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
    private Integer hashcode;
    private String ticketUsedDate;

    @ManyToOne
    @JoinColumn(name = "transactionId") //many-to-one relationship with Transaction
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "ticketTypeId") //many-to-one relationship with TicketType
    private TicketType ticketType;

    public Ticket(Long ticketId, Integer hashcode, String ticketUsedDate, TicketType ticketType) {
        this.ticketId = ticketId;
        this.hashcode = hashcode;
        this.ticketUsedDate = ticketUsedDate;
        this.ticketType = ticketType;
    }

    public Ticket() {
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getHashcode() {
        return hashcode;
    }

    public void setHashcode(Integer hashcode) {
        this.hashcode = hashcode;
    }

    public String getTicketUsedDate() {
        return ticketUsedDate;
    }

    public void setTicketUsedDate(String ticketUsedDate) {
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
    @Override
    public String toString() {
        return "Ticket [ticketId=" + ticketId + ", hashcode=" + hashcode + ", ticketUsedDate="
                + ticketUsedDate + ", ticketType=" + ticketType;
    }
}
