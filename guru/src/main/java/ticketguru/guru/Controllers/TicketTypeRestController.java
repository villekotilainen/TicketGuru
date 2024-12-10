package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.transaction.annotation.Transactional;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Repositories.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickettypes")
public class TicketTypeRestController {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @GetMapping("/event/{eventId}")
    public List<TicketType> getTicketTypesByEventId(@PathVariable Long eventId) {
        return ticketTypeRepository.findByEventEventId(eventId);
    }

    // PUT: Päivitä olemassa oleva lipputyyppi
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateTicketType(@PathVariable Long id, @RequestBody TicketType updatedTicketType) {
        Optional<TicketType> optionalTicketType = ticketTypeRepository.findById(id);

        if (optionalTicketType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TicketType not found");
        }

        TicketType existingTicketType = optionalTicketType.get();

        // Update fields if present in the request
        if (updatedTicketType.getTypeName() != null) {
            existingTicketType.setTypeName(updatedTicketType.getTypeName());
        }
        if (updatedTicketType.getTicketPrice() != null) {
            existingTicketType.setTicketPrice(updatedTicketType.getTicketPrice());
        }
        if (updatedTicketType.getTotalCount() != null) {
            existingTicketType.setTotalCount(updatedTicketType.getTotalCount());
        }
        if (updatedTicketType.getEvent() != null && updatedTicketType.getEvent().getEventId() != null) {
            existingTicketType.setEvent(updatedTicketType.getEvent());
        }

        // Save updated entity
        TicketType savedTicketType = ticketTypeRepository.save(existingTicketType);

        // Return updated entity
        return ResponseEntity.ok(savedTicketType);
    }
}
