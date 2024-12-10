package ticketguru.guru.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Entities.Transaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByTicketType_Event(Event event);

    List<Ticket> findByTransaction(Transaction transaction);

    Optional<Ticket> findByHashcode(String hashcode);

    boolean existsByTicketType(TicketType ticketType);
}
