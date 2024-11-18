package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketTest {

    private Ticket ticket;
    private TicketType mockTicketType;
    private Transaction mockTransaction;

    @BeforeEach
    void setUp() {
        mockTicketType = mock(TicketType.class);
        mockTransaction = mock(Transaction.class);

        ticket = new Ticket(
                1L,
                "uniquehash123",
                LocalDateTime.of(2023, 11, 15, 10, 30),
                mockTicketType,
                mockTransaction
        );
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, ticket.getTicketId());
        assertEquals("uniquehash123", ticket.getHashcode());
        assertEquals(LocalDateTime.of(2023, 11, 15, 10, 30), ticket.getTicketUsedDate());
        assertEquals(mockTicketType, ticket.getTicketType());
        assertEquals(mockTransaction, ticket.getTransaction());
        assertFalse(ticket.isUsed());

        // Test setters
        ticket.setTicketId(2L);
        ticket.setHashcode("newhash456");
        ticket.setTicketUsedDate(LocalDateTime.of(2023, 11, 16, 12, 45));
        TicketType newMockTicketType = mock(TicketType.class);
        ticket.setTicketType(newMockTicketType);
        Transaction newMockTransaction = mock(Transaction.class);
        ticket.setTransaction(newMockTransaction);
        ticket.setUsed(true);

        assertEquals(2L, ticket.getTicketId());
        assertEquals("newhash456", ticket.getHashcode());
        assertEquals(LocalDateTime.of(2023, 11, 16, 12, 45), ticket.getTicketUsedDate());
        assertEquals(newMockTicketType, ticket.getTicketType());
        assertEquals(newMockTransaction, ticket.getTransaction());
        assertTrue(ticket.isUsed());
    }

    @Test
    void testConstructorWithoutRelations() {
        Ticket simpleTicket = new Ticket(
                3L,
                "simplehash789",
                LocalDateTime.of(2023, 11, 17, 8, 20)
        );

        assertEquals(3L, simpleTicket.getTicketId());
        assertEquals("simplehash789", simpleTicket.getHashcode());
        assertEquals(LocalDateTime.of(2023, 11, 17, 8, 20), simpleTicket.getTicketUsedDate());
        assertNull(simpleTicket.getTicketType());
        assertNull(simpleTicket.getTransaction());
    }

    @Test
    void testDefaultConstructor() {
        Ticket defaultTicket = new Ticket();

        assertNull(defaultTicket.getTicketId());
        assertNull(defaultTicket.getHashcode());
        assertNull(defaultTicket.getTicketUsedDate());
        assertNull(defaultTicket.getTicketType());
        assertNull(defaultTicket.getTransaction());
        assertFalse(defaultTicket.isUsed());
    }

    @Test
    void testToString() {
        String toStringOutput = ticket.toString();
        assertTrue(toStringOutput.contains("uniquehash123"));
        assertTrue(toStringOutput.contains("2023-11-15T10:30"));
        assertTrue(toStringOutput.contains("false"));
    }
}
