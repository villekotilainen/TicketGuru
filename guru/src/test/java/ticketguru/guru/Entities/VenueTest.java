package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VenueTest {
    private Venue venue;
    private List<Event> events;

    @BeforeEach
    void setUp() {
        // Initialize test objects
        venue = new Venue(1L, "Grand Arena", "123 Main St", "A large event venue.");
        events = new ArrayList<>();

        // Add mock Event objects
        Event event1 = new Event(1L, "Concert", LocalDate.of(2025, 6, 27), LocalDate.of(2025, 6, 29), "Test1", venue);
        Event event2 = new Event(2L, "Conference", LocalDate.of(2025, 6, 27), LocalDate.of(2025, 6, 29), "Test2", venue);
        events.add(event1);
        events.add(event2);
    }

    @Test
    void testGetVenueId() {
        assertEquals(1L, venue.getVenueId());
    }

    @Test
    void testSetVenueId() {
        venue.setVenueId(2L);
        assertEquals(2L, venue.getVenueId());
    }

    @Test
    void testGetVenueName() {
        assertEquals("Grand Arena", venue.getVenueName());
    }

    @Test
    void testSetVenueName() {
        venue.setVenueName("City Hall");
        assertEquals("City Hall", venue.getVenueName());
    }

    @Test
    void testGetAddress() {
        assertEquals("123 Main St", venue.getAddress());
    }

    @Test
    void testSetAddress() {
        venue.setAddress("456 Elm St");
        assertEquals("456 Elm St", venue.getAddress());
    }

    @Test
    void testGetVenueDescription() {
        assertEquals("A large event venue.", venue.getVenueDescription());
    }

    @Test
    void testSetVenueDescription() {
        venue.setVenueDescription("A small, cozy venue.");
        assertEquals("A small, cozy venue.", venue.getVenueDescription());
    }

    @Test
    void testGetEvents() {
        venue.setEvents(events);
        assertEquals(2, venue.getEvents().size());
    }

    @Test
    void testSetEvents() {
        venue.setEvents(events);
        List<Event> retrievedEvents = venue.getEvents();
        assertNotNull(retrievedEvents);
        assertEquals(2, retrievedEvents.size());
        assertEquals("Concert", retrievedEvents.get(0).getEventName());
        assertEquals("Conference", retrievedEvents.get(1).getEventName());
    }

    @Test
    void testToString() {
        String expected = "Venue [venueId=1, venueName=Grand Arena, adress=123 Main St, venueDescription=A large event venue.]";
        assertEquals(expected, venue.toString());
    }

    @Test
    void testEmptyConstructor() {
        Venue emptyVenue = new Venue();
        assertNull(emptyVenue.getVenueId());
        assertNull(emptyVenue.getVenueName());
        assertNull(emptyVenue.getAddress());
        assertNull(emptyVenue.getVenueDescription());
        assertNull(emptyVenue.getEvents());
    }
}
