package ticketguru.guru;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Entities.Venue;

public class GuruRelationshipTests {

    @Test
    public void testEventVenueRelationship() {
    
        Venue venue = new Venue(1L, "Stadium", null, null);
        Event event = new Event(1L, "Concert", LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 20), "Live music event", venue);

    
        Venue associatedVenue = event.getVenue();

        
        assertNotNull(associatedVenue, "Event should have an associated venue.");
        assertEquals(1L, associatedVenue.getVenueId());
        assertEquals("Stadium", associatedVenue.getVenueName());
    }


    @Test
    public void testEventTicketTypes() {
        
        Venue venue = new Venue(1L, "Stadium", null, null);
        TicketType ticketType1 = new TicketType(1L, "VIP", 100.0);
        TicketType ticketType2 = new TicketType(2L, "Regular", 50.0);
        Event event = new Event(1L, "Concert", LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 20), "Live music event", venue);
        event.setTicketTypes(Arrays.asList(ticketType1, ticketType2));

        
        List<TicketType> ticketTypes = event.getTicketTypes();

        
        assertNotNull(ticketTypes, "Event should have ticket types.");
        assertEquals(2, ticketTypes.size(), "Event should have exactly two ticket types.");
        assertEquals("VIP", ticketTypes.get(0).getTypeName());
        assertEquals("Regular", ticketTypes.get(1).getTypeName());
    }


    @Test
    public void testEventWithNullVenue() {
    
        Event event = new Event(1L, "Concert", LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 20), "Live music event", null);

    
        Venue venue = event.getVenue();

    
        assertNull(venue, "Event venue should be null.");
    }

    @Test
    public void testInvalidEventDateRange() {
    
        Venue venue = new Venue(1L, "Stadium", null, null);

    
        assertThrows(IllegalArgumentException.class, () -> {
            Event event = new Event(1L, "Concert", LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 18), "Live music event", venue);
        }, "End time cannot be before start time");
    }
}
