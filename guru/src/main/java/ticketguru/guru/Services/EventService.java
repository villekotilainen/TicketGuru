package ticketguru.guru.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.EventRepository;
import ticketguru.guru.Repositories.VenueRepository;
import ticketguru.guru.dto.EventDTO;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public Event createEvent(Event event) {
        Venue venue = event.getVenue();
        if (venue != null && venue.getVenueId() == null) {
            venue = venueRepository.save(venue); // Persist venue if it's new
            event.setVenue(venue);
        }
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, EventDTO eventDTO) throws EntityNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setEventName(eventDTO.getEventName());
            event.setStartTime(eventDTO.getStartTime());
            event.setEndTime(eventDTO.getEndTime());
            event.setEventDescription(eventDTO.getEventDescription());

            Venue venue = venueRepository.findByVenueName(eventDTO.getVenueName());
            if (venue != null) {
                event.setVenue(venue);
            } else {
                throw new EntityNotFoundException("Venue not found with name: " + eventDTO.getVenueName());
            }

            return eventRepository.save(event);
        } else {
            throw new EntityNotFoundException("Event not found with id: " + id);
        }
    }

    public List<Venue> searchVenuesByAddress(String addressPart) {
        return venueRepository.findByAddressContaining(addressPart);
    }
}

