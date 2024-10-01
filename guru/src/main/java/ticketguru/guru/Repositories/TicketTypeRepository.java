package ticketguru.guru.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long>{

    List<TicketType> findByEventEventId(Long eventId);
     
    List<TicketType> findByTypeName(String typeName);
}
