package ticketguru.guru.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long>{

}
