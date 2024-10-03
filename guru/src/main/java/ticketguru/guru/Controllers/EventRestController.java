package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Repositories.EventRepository;

@RestController
@RequestMapping("/api/events") // Base path for events-related requests
public class EventRestController {

    @Autowired
    private EventRepository eventRepository;

    // GET: Hae kaikki tapahtumat
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // GET: Hae yksittäinen tapahtuma ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Lisää uusi tapahtuma
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event newEvent) {
        Event savedEvent = eventRepository.save(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    // PUT: Päivitä olemassa oleva tapahtuma
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        
        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();
            existingEvent.setEventName(eventDetails.getEventName());
            existingEvent.setStartTime(eventDetails.getStartTime());
            existingEvent.setEndTime(eventDetails.getEndTime());
            existingEvent.setEventDescription(eventDetails.getEventDescription());
            existingEvent.setVenue(eventDetails.getVenue());
            
            Event updatedEvent = eventRepository.save(existingEvent);
            return ResponseEntity.ok(updatedEvent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Poista tapahtuma ID:n perusteella
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
