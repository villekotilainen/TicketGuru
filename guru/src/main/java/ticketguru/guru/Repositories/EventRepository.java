package ticketguru.guru.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Venue;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByVenue(Venue venue);

    List<Event> findByEventName(String eventName);

    List<Event> findByStartTimeAfter(LocalDate date);

    List<Event> findByStartTimeBetween(LocalDate startDate, LocalDate endDate);
}
