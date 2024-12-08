package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Repositories.EventRepository;
import ticketguru.guru.Repositories.TicketTypeRepository;
import ticketguru.guru.Services.EventService;
import ticketguru.guru.dto.EventDTO;
import ticketguru.guru.Exceptions.EventNotFoundException;

@RestController
@RequestMapping("/api/events") // Base path for events-related requests
public class EventRestController {

    private final EventRepository eventRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final EventService eventService;


    public EventRestController(EventRepository eventRepository, TicketTypeRepository ticketTypeRepository, EventService eventService) {
        this.eventRepository = eventRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventService = eventService;
    }

    // GET: Retrieve all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // GET: Retrieve a single event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Create a new event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event newEvent) {
        Event savedEvent = eventRepository.save(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    // POST: Add a ticket type to an existing event
    @PostMapping("/{eventId}/tickettypes")
    public ResponseEntity<TicketType> createTicketType(@PathVariable Long eventId, @RequestBody TicketType ticketType) {
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

    // PUT: Update an existing event
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDTO);
            return ResponseEntity.ok(updatedEvent);
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating event: " + e.getMessage());
        }
    }

    // DELETE: Delete an event by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
