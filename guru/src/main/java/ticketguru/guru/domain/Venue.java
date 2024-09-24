package ticketguru.guru.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long venueId;
    private String venueName;
    private String adress;
    private String venueDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venue") //one-to-many relationship with Event
    private List<Event> events;

    public Venue(Long venueId, String venueName, String adress, String venueDescription) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.adress = adress;
        this.venueDescription = venueDescription;
    }

    public Venue() {
        this.venueId = null;
        this.venueName = null;
        this.adress = null;
        this.venueDescription = null;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getVenueDescription() {
        return venueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        this.venueDescription = venueDescription;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Venue [venueId=" + venueId + ", venueName=" + venueName + ", adress=" + adress + ", venueDescription="
                + venueDescription + "]";
    }
}
