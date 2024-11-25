/* 
package ticketguru.guru.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private Transaction transaction;
    private TGUser mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mock(TGUser.class);
        transaction = new Transaction(1L, LocalDateTime.of(2024, 11, 18, 10, 0), 99.99, true, mockUser);
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, transaction.getTransactionId());
        assertEquals(LocalDateTime.of(2024, 11, 18, 10, 0), transaction.getTransactionDate());
        assertEquals(99.99, transaction.getTotalSum());
        assertTrue(transaction.getSucceeded());
        assertEquals(mockUser, transaction.getUser());
        assertNull(transaction.getTickets());

        // Test setters
        transaction.setTransactionId(2L);
        transaction.setTransactionDate(LocalDateTime.of(2024, 12, 1, 15, 0));
        transaction.setTotalSum(150.75);
        transaction.setSucceeded(false);
        TGUser newMockUser = mock(TGUser.class);
        transaction.setUser(newMockUser);

        assertEquals(2L, transaction.getTransactionId());
        assertEquals(LocalDateTime.of(2024, 12, 1, 15, 0), transaction.getTransactionDate());
        assertEquals(150.75, transaction.getTotalSum());
        assertFalse(transaction.getSucceeded());
        assertEquals(newMockUser, transaction.getUser());
    }

    @Test
    void testDefaultConstructor() {
        Transaction defaultTransaction = new Transaction();

        assertNull(defaultTransaction.getTransactionId());
        assertNull(defaultTransaction.getTransactionDate());
        assertNull(defaultTransaction.getTotalSum());
        assertNull(defaultTransaction.getSucceeded());
        assertNull(defaultTransaction.getUser());
        assertNull(defaultTransaction.getTickets());
    }

    @Test
    void testConstructorWithParameters() {
        Transaction newTransaction = new Transaction(3L, LocalDateTime.of(2024, 11, 20, 14, 30), 200.00, false, mockUser);

        assertEquals(3L, newTransaction.getTransactionId());
        assertEquals(LocalDateTime.of(2024, 11, 20, 14, 30), newTransaction.getTransactionDate());
        assertEquals(200.00, newTransaction.getTotalSum());
        assertFalse(newTransaction.getSucceeded());
        assertEquals(mockUser, newTransaction.getUser());
        assertNull(newTransaction.getTickets());
    }

    @Test
    void testSetTickets() {
        Ticket mockTicket1 = mock(Ticket.class);
        Ticket mockTicket2 = mock(Ticket.class);

        transaction.setTickets(List.of(mockTicket1, mockTicket2));
        List<Ticket> tickets = transaction.getTickets();

        assertNotNull(tickets);
        assertEquals(2, tickets.size());
        assertEquals(mockTicket1, tickets.get(0));
        assertEquals(mockTicket2, tickets.get(1));
    }

    @Test
    void testToString() {
        String toStringOutput = transaction.toString();

        assertTrue(toStringOutput.contains("transactionId=1"));
        assertTrue(toStringOutput.contains("transactionDate=2024-11-18T10:00"));
        assertTrue(toStringOutput.contains("totalSum=99.99"));
        assertTrue(toStringOutput.contains("succeeded=true"));
        assertTrue(toStringOutput.contains("user=" + mockUser));
    }
}
*/