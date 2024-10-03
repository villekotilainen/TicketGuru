package ticketguru.guru.DTOs;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.TicketType;

public class TicketTypeDTO {
    private Long id;
    private String typeName;
    private double ticketPrice;
    private int totalCount;
    private String eventName;
    private String venueName;
    private String eventUrl; // Linkki tapahtuman urliin

    public TicketTypeDTO(TicketType ticketType) {
        this.id = ticketType.getTicketTypeId();
        this.typeName = ticketType.getTypeName();
        this.ticketPrice = ticketType.getTicketPrice();
        this.totalCount = ticketType.getTotalCount();

        if (ticketType.getEvent() != null) {
            Event event = ticketType.getEvent();
            this.eventName = event.getEventName();
            if (event.getVenue() != null) {
                this.venueName = event.getVenue().getVenueName();
            }

            this.eventUrl = "/api/events/" + event.getEventId(); 
        }
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }


    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }


    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }


    public Long getId() {
        return id;
    }


    public String getTypeName() {
        return typeName;
    }


    public double getTicketPrice() {
        return ticketPrice;
    }


    public int getTotalCount() {
        return totalCount;
    }


    public String getEventName() {
        return eventName;
    }


    public String getVenueName() {
        return venueName;
    }


    public String getEventUrl() {
        return eventUrl;
    }



}
