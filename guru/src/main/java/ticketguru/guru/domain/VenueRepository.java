package ticketguru.guru.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    Venue findByVenueName(String venueName);

    List<Venue> findByAddressContaining(String address);
}
