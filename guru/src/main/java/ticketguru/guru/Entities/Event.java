package ticketguru.guru.Entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;
    private String eventName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    
    private String eventDescription;

    @ManyToOne
    @JoinColumn(name = "venueId") //many-to-one relationship with Venue
    private Venue venue;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true) //one-to-many relationship with TicketType
    private List<TicketType> ticketTypes;

    public Event(Long eventId, String eventName, LocalDateTime startTime, LocalDateTime endTime, String eventDescription, Venue venue) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDescription = eventDescription;
        this.venue = venue;
    }
    
    public Event() {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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

    public List<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", eventName=" + eventName + ", startTime=" + startTime + ", endTime="
                + endTime + ", eventDescription=" + eventDescription + ", venue=" + venue + "]";
    }
}
