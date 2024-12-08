/* 
package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

    private Event event;
    private Venue mockVenue;
    private TicketType mockTicketType1;
    private TicketType mockTicketType2;

    @BeforeEach
    void setUp() {
        mockVenue = mock(Venue.class);
        mockTicketType1 = mock(TicketType.class);
        mockTicketType2 = mock(TicketType.class);

        event = new Event(
                1L,
                "Concert",
                LocalDateTime.of(2023, 11, 18),
                LocalDateTime.of(2023, 11, 19),
                "A fantastic music concert.",
                mockVenue
        );
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, event.getEventId());
        assertEquals("Concert", event.getEventName());
        assertEquals(LocalDate.of(2023, 11, 18), event.getStartTime());
        assertEquals(LocalDate.of(2023, 11, 19), event.getEndTime());
        assertEquals( "A fantastic music concert.", event.getEventDescription());
        assertEquals(mockVenue, event.getVenue());

         // Test setters
         event.setEventId(2L);
         event.setEventName("Conference");
         event.setStartTime(LocalDate.of(2023, 12, 1));
         event.setEndTime(LocalDate.of(2023, 12, 2));
         event.setEventDescription("A tech conference.");
         Venue newMockVenue = mock(Venue.class);
         event.setVenue(newMockVenue);

        assertEquals(2L, event.getEventId());
        assertEquals("Conference", event.getEventName());
        assertEquals(LocalDate.of(2023, 12, 1), event.getStartTime());
        assertEquals(LocalDate.of(2023, 12, 2), event.getEndTime());
        assertEquals("A tech conference.", event.getEventDescription());
        assertEquals(newMockVenue, event.getVenue());
    }

    @Test
    void testTicketTypesRelationship() {
        // Test One-to-Many relationship
        List<TicketType> ticketTypes = Arrays.asList(mockTicketType1, mockTicketType2);
        event.setTicketTypes(ticketTypes);

        assertEquals(2, event.getTicketTypes().size());
        assertTrue(event.getTicketTypes().contains(mockTicketType1));
        assertTrue(event.getTicketTypes().contains(mockTicketType2));
    }

    @Test
    void testToString() {
        String expected = "Event [eventId=1, eventName=Concert, startTime=2023-11-18, endTime=2023-11-19, "
                + "eventDescription=A fantastic music concert., venue=" + mockVenue + "]";
        assertTrue(event.toString().contains("Concert"));
    }
    
    @Test
    void testDefaultConstructor() {
        Event defaultEvent = new Event();
        assertNull(defaultEvent.getEventId());
        assertNull(defaultEvent.getEventName());
        assertNull(defaultEvent.getStartTime());
        assertNull(defaultEvent.getEndTime());
        assertNull(defaultEvent.getEventDescription());
        assertNull(defaultEvent.getVenue());
        assertNull(defaultEvent.getTicketTypes());
    }

}
    */
    