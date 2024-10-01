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
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketTypeId;
    private Double ticketPrice;
    private String typeName;
    private Integer totalCount;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketType")
    private List<Ticket> tickets; 

    @ManyToOne
    @JoinColumn(name = "eventId") //many-to-one relationship with Event
    private Event event;

    public TicketType(Long ticketTypeId, Double ticketPrice, String typeName, Integer totalCount) {
        this.ticketTypeId = ticketTypeId;
        this.ticketPrice = ticketPrice;
        this.typeName = typeName;
        this.totalCount = totalCount;
    }  

    public TicketType() {
    }

    public Long getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Long ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "TicketType [ticketTypeId=" + ticketTypeId + ", ticketPrice=" + ticketPrice + ", typeName=" + typeName
                + ", totalCount=" + totalCount + "]";
    }  
}
