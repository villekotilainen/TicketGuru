package ticketguru.guru.dto;


import java.util.List;

public class SaleRequest {

    private List<String> ticketTypes; // Lista lipputyypeistä
    private String user; // Asiakkaan nimi tai ID
    private String eventId; // Tapahtuman ID (mikäli haluat käsitellä tapahtumia)

    // Getterit ja setterit
    public List<String> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<String> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
