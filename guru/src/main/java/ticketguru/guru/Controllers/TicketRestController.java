package ticketguru.guru.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Entities.Transaction;
import ticketguru.guru.Repositories.TGUserRepository;
import ticketguru.guru.Repositories.TicketRepository;
import ticketguru.guru.Repositories.TicketTypeRepository;
import ticketguru.guru.Repositories.TransactionRepository;

@RestController
@RequestMapping("/api/tickets") // Base path for tickets-related requests
@Validated
public class TicketRestController {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TGUserRepository tgUserRepository;

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
        if (newTicket.getTransaction() == null || newTicket.getTicketType() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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

  // POST: Create Tickets for a Transaction
@CrossOrigin(origins = "*")
@Transactional
@PostMapping("/transaction")
public ResponseEntity<?> createTicketsWithTransaction(
        @RequestBody List<Ticket> tickets,
        @RequestParam Long transactionId) {

    try {
        // Validate if the transaction exists
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (transactionOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Transaction ID is invalid or does not exist.");
        }

        Transaction transaction = transactionOptional.get();

        // Validate tickets list
        if (tickets == null || tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Ticket list cannot be empty.");
        }

        // Associate tickets with the transaction and set required fields
        for (Ticket ticket : tickets) {
            if (ticket.getTicketType() == null || ticket.getTicketType().getTicketTypeId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Each ticket must have a valid TicketType.");
            }

            // Check if the ticket type exists
            TicketType ticketType = ticketTypeRepository.findById(ticket.getTicketType().getTicketTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid TicketType ID: " + ticket.getTicketType().getTicketTypeId()));

            // Set valid TicketType and Transaction
            ticket.setTicketType(ticketType);
            ticket.setTransaction(transaction);

            // Generate a unique hashcode for the ticket if not already set
            if (ticket.getHashcode() == null || ticket.getHashcode().isEmpty()) {
                ticket.setHashcode(UUID.randomUUID().toString());
            }
        }

        // Save tickets
        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);

        // Return the saved tickets
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTickets);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error occurred while creating tickets: " + e.getMessage());
    }
}
    // POST: Create Tickets with User and Transaction
    @CrossOrigin(origins = "*")
    @PostMapping("/create-with-user")
    public ResponseEntity<?> createTicketsWithUser(
            @RequestBody List<Ticket> tickets,
            @RequestParam Long userId) {
        Optional<TGUser> userOptional = tgUserRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID is invalid.");
        }

        TGUser user = userOptional.get();

        // Create a new transaction for the tickets
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setUser(user);
        transaction.setTotalSum(tickets.stream()
                .mapToDouble(ticket -> ticket.getTicketType().getTicketPrice())
                .sum());
        transaction.setSucceeded(true);
        transactionRepository.save(transaction);

        // Associate tickets with the transaction and save
        tickets.forEach(ticket -> ticket.setTransaction(transaction));
        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTickets);
    }
}
