package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.EventRepository;
import ticketguru.guru.Repositories.TicketTypeRepository;
import ticketguru.guru.Repositories.VenueRepository;
import ticketguru.guru.Services.EventService;
import ticketguru.guru.dto.EventDTO;
import ticketguru.guru.Exceptions.EventNotFoundException;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final EventService eventService;

    public EventRestController(EventRepository eventRepository, VenueRepository venueRepository,
            EventService eventService, TicketTypeRepository ticketTypeRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable Long eventId) {
        try {
            Optional<Event> event = eventRepository.findById(eventId);
            if (event.isPresent()) {
                return ResponseEntity.ok(event.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event with ID " + eventId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving event: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event newEvent) {
        try {
            // Save the event first
            Event savedEvent = eventService.createEvent(newEvent);

            // If the event includes ticket types, associate them with the event and save
            if (newEvent.getTicketTypes() != null && !newEvent.getTicketTypes().isEmpty()) {
                for (TicketType ticketType : newEvent.getTicketTypes()) {
                    ticketType.setEvent(savedEvent); // Link the ticket type to the saved event
                    ticketTypeRepository.save(ticketType);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating event: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
   public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
    try {
        // Find the existing event
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }

        Event event = optionalEvent.get();

        // Update the event's basic details
        event.setEventName(updatedEvent.getEventName());
        event.setStartTime(updatedEvent.getStartTime());
        event.setEndTime(updatedEvent.getEndTime());
        event.setEventDescription(updatedEvent.getEventDescription());

        // Update or create venue
        if (updatedEvent.getVenue() != null) {
            Optional<Venue> optionalVenue = venueRepository.findById(updatedEvent.getVenue().getVenueId());
            if (optionalVenue.isPresent()) {
                event.setVenue(optionalVenue.get());
            } else {
                event.setVenue(updatedEvent.getVenue());
            }
        }

        // Save the updated event
        Event savedEvent = eventRepository.save(event);

        // Update ticket types
        if (updatedEvent.getTicketTypes() != null) {
            for (TicketType ticketType : updatedEvent.getTicketTypes()) {
                if (ticketType.getTicketTypeId() != null) {
                    // Update existing ticket type
                    Optional<TicketType> optionalTicketType = ticketTypeRepository.findById(ticketType.getTicketTypeId());
                    if (optionalTicketType.isPresent()) {
                        TicketType existingTicketType = optionalTicketType.get();
                        existingTicketType.setTypeName(ticketType.getTypeName());
                        existingTicketType.setTicketPrice(ticketType.getTicketPrice());
                        existingTicketType.setEvent(savedEvent); // Associate with the updated event
                        ticketTypeRepository.save(existingTicketType);
                    }
                } else {
                    // Create new ticket type
                    ticketType.setEvent(savedEvent);
                    ticketTypeRepository.save(ticketType);
                }
            }
        }

        return ResponseEntity.ok(savedEvent);

    } catch (Exception e) {
        e.printStackTrace(); // Log the error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating event: " + e.getMessage());
    }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/{eventId}/tickettypes")
    public ResponseEntity<TicketType> addTicketTypeToEvent(
            @PathVariable Long eventId,
            @RequestBody TicketType ticketType) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            ticketType.setEvent(event);
            TicketType savedTicketType = ticketTypeRepository.save(ticketType);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTicketType);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
