package ticketguru.guru.domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;
    private String eventName;
    private Date startTime;
    private Date endTime;
    private String eventDescription;

    @ManyToOne
    @JoinColumn(name = "venueId") //many-to-one relationship with Venue
    private Venue venue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event") //one-to-many relationship with Ticket
    private List<Ticket> tickets;

    public Event(Long eventId, String eventName, Date startTime, Date endTime, String eventDescription, Venue venue) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDescription = eventDescription;
        this.venue = venue;
    }

    public Event() {
        this.eventId = null;
        this.eventName = null;
        this.startTime = null;
        this.endTime = null;
        this.eventDescription = null;
        this.venue = null;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", eventName=" + eventName + ", startTime=" + startTime + ", endTime="
                + endTime + ", eventDescription=" + eventDescription + ", venue=" + venue + "]";
    }
}
