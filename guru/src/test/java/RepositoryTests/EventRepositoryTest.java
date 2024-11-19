
package RepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.EventRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest // Sets up an in-memory database for testing repositories
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setup() {
        Venue venue1 = new Venue(2L, "Stadium A", null, null);
        Venue venue2 = new Venue(1L, "Arena B", null, null);

        Event event1 = new Event(null, "Concert A", LocalDate.of(2024, 1, 15), null, null, venue1);
        Event event2 = new Event(null, "Concert B", LocalDate.of(2024, 2, 20), null, null, venue2);
        Event event3 = new Event(null, "Concert A", LocalDate.of(2024, 3, 25), null, null, venue2);

        // Save venues and events
        eventRepository.saveAll(List.of(event1, event2, event3));
    }

    @Test
    void shouldFindEventsByVenue() {
        Venue venue = new Venue(1L, "Arena B", null, null);

        List<Event> events = eventRepository.findByVenue(venue);

        assertThat(events).hasSize(3);
        assertThat(events.get(1).getEventName()).isEqualTo("Concert B");
    }

    @Test
    void shouldFindEventsByEventName() {
        List<Event> events = eventRepository.findByEventName("Concert A");

        assertThat(events).hasSize(2);
        assertThat(events.get(0).getStartTime()).isEqualTo(LocalDate.of(2024, 1, 15));
    }

    @Test
    void shouldFindEventsStartingAfterDate() {
        LocalDate date = LocalDate.of(2024, 2, 1);

        List<Event> events = eventRepository.findByStartTimeAfter(date);

        assertThat(events).hasSize(5);
        assertThat(events.get(3).getEventName()).isEqualTo("Concert B");
    }

    @Test
    void shouldFindEventsBetweenDates() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 28);

        List<Event> events = eventRepository.findByStartTimeBetween(startDate, endDate);

        assertThat(events).hasSize(2);
        assertThat(events.get(1).getEventName()).isEqualTo("Concert B");
    }
}
