package ticketguru.guru.dto;

import java.time.LocalDateTime;

public class EventDTO {
    
    private Long eventId;
    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventDescription;
    private String venueName;

    // Default constructor
    public EventDTO() {
    }

    // Constructor with fields
    public EventDTO(Long eventId, String eventName, LocalDateTime startTime, LocalDateTime endTime, String eventDescription, String venueName) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDescription = eventDescription;
        this.venueName = venueName;
    }

    // Getters and Setters

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

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
}
