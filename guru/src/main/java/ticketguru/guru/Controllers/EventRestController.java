package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.TicketType;
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

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDTO);
            return ResponseEntity.ok(updatedEvent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating event: " + e.getMessage());
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
