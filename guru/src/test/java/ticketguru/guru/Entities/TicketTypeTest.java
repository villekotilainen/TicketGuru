package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketTypeTest {

    private TicketType ticketType;
    private Event mockEvent;

    @BeforeEach
    void setUp() {
        mockEvent = mock(Event.class);
        ticketType = new TicketType(1L, 25.99, "VIP", 100);
        ticketType.setEvent(mockEvent);
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, ticketType.getTicketTypeId());
        assertEquals(25.99, ticketType.getTicketPrice());
        assertEquals("VIP", ticketType.getTypeName());
        assertEquals(100, ticketType.getTotalCount());
        assertEquals(mockEvent, ticketType.getEvent());
        assertNull(ticketType.getTickets());

        // Test setters
        ticketType.setTicketTypeId(2L);
        ticketType.setTicketPrice(15.50);
        ticketType.setTypeName("Regular");
        ticketType.setTotalCount(200);
        Event newMockEvent = mock(Event.class);
        ticketType.setEvent(newMockEvent);

        assertEquals(2L, ticketType.getTicketTypeId());
        assertEquals(15.50, ticketType.getTicketPrice());
        assertEquals("Regular", ticketType.getTypeName());
        assertEquals(200, ticketType.getTotalCount());
        assertEquals(newMockEvent, ticketType.getEvent());
    }

    @Test
    void testDefaultConstructor() {
        TicketType defaultTicketType = new TicketType();

        assertNull(defaultTicketType.getTicketTypeId());
        assertNull(defaultTicketType.getTicketPrice());
        assertNull(defaultTicketType.getTypeName());
        assertNull(defaultTicketType.getTotalCount());
        assertNull(defaultTicketType.getTickets());
        assertNull(defaultTicketType.getEvent());
    }

    @Test
    void testConstructorWithParameters() {
        TicketType newTicketType = new TicketType(3L, 50.0, "Premium", 50);

        assertEquals(3L, newTicketType.getTicketTypeId());
        assertEquals(50.0, newTicketType.getTicketPrice());
        assertEquals("Premium", newTicketType.getTypeName());
        assertEquals(50, newTicketType.getTotalCount());
        assertNull(newTicketType.getTickets());
        assertNull(newTicketType.getEvent());
    }

    @Test
    void testSetTickets() {
        Ticket mockTicket1 = mock(Ticket.class);
        Ticket mockTicket2 = mock(Ticket.class);

        ticketType.setTickets(List.of(mockTicket1, mockTicket2));
        List<Ticket> tickets = ticketType.getTickets();

        assertNotNull(tickets);
        assertEquals(2, tickets.size());
        assertEquals(mockTicket1, tickets.get(0));
        assertEquals(mockTicket2, tickets.get(1));
    }

    @Test
    void testToString() {
        String toStringOutput = ticketType.toString();

        assertTrue(toStringOutput.contains("ticketTypeId=1"));
        assertTrue(toStringOutput.contains("ticketPrice=25.99"));
        assertTrue(toStringOutput.contains("typeName=VIP"));
        assertTrue(toStringOutput.contains("totalCount=100"));
    }
}
