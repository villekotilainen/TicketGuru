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
    public ResponseEntity<TicketType> updateTicketType(@PathVariable Long id,
            @RequestBody TicketType ticketTypeDetails) {
        Optional<TicketType> optionalTicketType = ticketTypeRepository.findById(id);
        if (!optionalTicketType.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        TicketType ticketType = optionalTicketType.get();
        ticketType.setTypeName(ticketTypeDetails.getTypeName());
        ticketType.setTicketPrice(ticketTypeDetails.getTicketPrice());
        ticketTypeRepository.save(ticketType);

        return ResponseEntity.ok(ticketType);
    }

    @GetMapping("/{id}/remaining")
    public ResponseEntity<?> getRemainingTickets(@PathVariable Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid TicketType ID"));
        return ResponseEntity.ok(ticketType.getRemainingCount());
    }

}
