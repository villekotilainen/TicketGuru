package ticketguru.guru;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.EventRepository;
import ticketguru.guru.Repositories.VenueRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

@DataJpaTest
public class EventPersistenceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Test
    public void testEventPersistence() {
        // Arrange
        Venue venue = new Venue(null, "Stadium", null, null);
        venueRepository.save(venue);  // Save venue to the database
        Event event = new Event(null, "Concert", LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 20), "Live music event", venue);
        
        // Act
        eventRepository.save(event);
        
        // Assert
        assertNotNull(event.getEventId(), "Event should be persisted with a valid eventId.");
    }
}


