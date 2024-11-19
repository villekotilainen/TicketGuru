package ticketguru.guru.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Repositories.TicketRepository;

@RestController
@RequestMapping("/api/tickets") // Base path for tickets-related requests
@Validated
public class TicketRestController {

    @Autowired
    private TicketRepository ticketRepository;

    // GET: Hae kaikki liput
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // GET: Hae yksittäinen lippu ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Lisää uusi lippu
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody Ticket newTicket) {
        Ticket savedTicket = ticketRepository.save(newTicket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    // PUT: Päivitä olemassa oleva lippu
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @Valid @RequestBody Ticket ticketDetails) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isPresent()) {
            Ticket existingTicket = optionalTicket.get();
            existingTicket.setHashcode(ticketDetails.getHashcode());
            existingTicket.setTicketUsedDate(ticketDetails.getTicketUsedDate());

            Ticket updatedTicket = ticketRepository.save(existingTicket);
            return ResponseEntity.ok(updatedTicket);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Poista lippu ID:n perusteella
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // POST: Validate ticket
    @CrossOrigin(origins = "*")
    @PostMapping("/validate")
    public ResponseEntity<String> validateTicket(@RequestBody Ticket ticket) {
        Optional<Ticket> existingTicket = ticketRepository.findById(ticket.getTicketId());

        if (existingTicket.isPresent() && !existingTicket.get().isUsed()) {
            existingTicket.get().setTicketUsedDate(LocalDateTime.now()); // Mark ticket as used
            ticketRepository.save(existingTicket.get());
            return ResponseEntity.ok("Ticket is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or already used ticket");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/transaction")
    public ResponseEntity<List<Ticket>> createTickets(@Valid @RequestBody List<Ticket> newTickets) {
        List<Ticket> savedTickets = ticketRepository.saveAll(newTickets);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTickets);
}
    
}
