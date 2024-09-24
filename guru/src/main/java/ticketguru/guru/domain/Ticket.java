package ticketguru.guru.domain;

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
    private String hashcode;
    private Double price;
    private String ticketUsedDate;

    @ManyToOne
    @JoinColumn(name = "eventId") //many-to-one relationship with Event
    private Event event;

    @ManyToOne
    @JoinColumn(name = "transactionId") //many-to-one relationship with Transaction
    private Transaction transaction;

    public Ticket(Long ticketId, String hashcode, Double price, String ticketUsedDate, Event event, Transaction transaction) {
        this.ticketId = ticketId;
        this.hashcode = hashcode;
        this.price = price;
        this.ticketUsedDate = ticketUsedDate;
        this.event = event;
        this.transaction = transaction;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTicketUsedDate() {
        return ticketUsedDate;
    }

    public void setTicketUsedDate(String ticketUsedDate) {
        this.ticketUsedDate = ticketUsedDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Ticket [ticketId=" + ticketId + ", hashcode=" + hashcode + ", price=" + price + ", ticketUsedDate="
                + ticketUsedDate + ", event=" + event + ", transaction=" + transaction + "]";
    }  
}
