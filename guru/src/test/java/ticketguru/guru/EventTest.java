package ticketguru.guru;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Venue;

public class EventTest {

    @Test
    public void testEventFields() {
        
        Venue venue = new Venue(1L, "Stadium", null, null); // Assuming Venue constructor is available
        Event event = new Event(1L, "Concert", LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 20), "Live music event", venue);

        
        assertEquals(1L, event.getEventId());
        assertEquals("Concert", event.getEventName());
        assertEquals(LocalDate.of(2024, 11, 19), event.getStartTime());
        assertEquals(LocalDate.of(2024, 11, 20), event.getEndTime());
        assertEquals("Live music event", event.getEventDescription());
        assertEquals(venue, event.getVenue());
    }
}
