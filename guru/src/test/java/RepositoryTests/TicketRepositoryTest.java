package RepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import ticketguru.guru.GuruApplication;
import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Entities.Transaction;
import ticketguru.guru.Repositories.EventRepository;
import ticketguru.guru.Repositories.TicketRepository;
import ticketguru.guru.Repositories.TicketTypeRepository;
import ticketguru.guru.Repositories.TransactionRepository;
import ticketguru.guru.Entities.TicketType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Configures an in-memory database and scans JPA repositories
@ContextConfiguration(classes = GuruApplication.class)
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Event event;
    private Transaction transaction;

    @BeforeEach
    void setup() {
        // Save the Event entity first
        Event event = new Event();
        event.setEventName("Concert");
        event = eventRepository.save(event);

        // Save the TicketType entity
        TicketType ticketType = new TicketType(null, 100.00, "student", 55);
        ticketType.setEvent(event);
        
        //ticketType = TicketTypeRepository.save(ticketType);

        // Save the Ticket entity, referencing the saved TicketType
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType); // Reference the saved TicketType
        ticket.setHashcode("HASH123");
        ticketRepository.save(ticket);
    }


    @Test
    void shouldFindTicketsByEvent() {
        // Act
        List<Ticket> tickets = ticketRepository.findByTicketType_Event(event);

        // Assert
        assertThat(tickets).isNotEmpty();
        assertThat(tickets).hasSize(2);
        assertThat(tickets.get(0).getTicketType().getEvent().getEventName()).isEqualTo("Concert");
    }

    @Test
    void shouldFindTicketsByTransaction() {
        // Act
        List<Ticket> tickets = ticketRepository.findByTransaction(transaction);

        // Assert
        assertThat(tickets).isNotEmpty();
        assertThat(tickets).hasSize(2);
        assertThat(tickets.get(0).getTransaction().getTransactionId()).isEqualTo("TXN123");
    }

    @Test
    void shouldFindTicketByHashcode() {
        // Act
        Ticket ticket = ticketRepository.findByHashcode("HASH123");

        // Assert
        assertThat(ticket).isNotNull();
        assertThat(ticket.getHashcode()).isEqualTo("HASH123");
    }

    @Test
    void shouldReturnEmptyListIfNoTicketsForEvent() {
        // Arrange
        Event anotherEvent = new Event();
        anotherEvent.setEventName("Different Event");
        anotherEvent = eventRepository.save(anotherEvent);

        // Act
        List<Ticket> tickets = ticketRepository.findByTicketType_Event(anotherEvent);

        // Assert
        assertThat(tickets).isEmpty();
    }

    @Test
    void shouldReturnNullIfHashcodeNotFound() {
        // Act
        Ticket ticket = ticketRepository.findByHashcode("NON_EXISTENT_HASH");

        // Assert
        assertThat(ticket).isNull();
    }
}
