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

import ticketguru.guru.DTOs.TicketTypeDTO;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Repositories.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickettypes") // Base path for tickettype-related requests
public class TicketTypeRestController {

    @Autowired
    private TicketTypeRepository tickettypeRepository;

    // GET: Hae kaikki lipputyypit
    @GetMapping
    public List<TicketType> getAllTicketTypes() {
        return tickettypeRepository.findAll();
    }

    // GET: Hae yksittäinen lipputyyppi ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<TicketTypeDTO> getTicketTypeById(@PathVariable Long id) {
        Optional<TicketType> tickettype = tickettypeRepository.findById(id);

        if (tickettype.isPresent()) {
            TicketTypeDTO ticketTypeDTO = new TicketTypeDTO(tickettype.get());
            return ResponseEntity.ok(ticketTypeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // POST: Lisää uusi lipputyyppi
    @PostMapping
    public ResponseEntity<TicketType> createTicketType(@RequestBody TicketType newTicketType) {
        TicketType savedTicketType = tickettypeRepository.save(newTicketType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicketType);
    }

    // PUT: Päivitä olemassa oleva lipputyyppi
    @PutMapping("/{id}")
    public ResponseEntity<TicketType> updateTicketType(@PathVariable Long id,
            @RequestBody TicketType tickettypeDetails) {
        Optional<TicketType> optionalTicketType = tickettypeRepository.findById(id);

        if (optionalTicketType.isPresent()) {
            TicketType existingTicketType = optionalTicketType.get();
            existingTicketType.setTicketPrice(tickettypeDetails.getTicketPrice());
            existingTicketType.setTypeName(tickettypeDetails.getTypeName());
            existingTicketType.setTotalCount(tickettypeDetails.getTotalCount());
            existingTicketType.setEvent(tickettypeDetails.getEvent());

            TicketType updatedTicketType = tickettypeRepository.save(existingTicketType);
            return ResponseEntity.ok(updatedTicketType);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Poista lipputyyppi ID:n perusteella
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketType(@PathVariable Long id) {
        if (tickettypeRepository.existsById(id)) {
            tickettypeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
