package ticketguru.guru.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByVenue(Venue venue);

    List<Event> findByEventName(String eventName);

    List<Event> findByStartTimeAfter(Date date);

    List<Event> findByStartTimeBetween(Date startDate, Date endDate);
}
