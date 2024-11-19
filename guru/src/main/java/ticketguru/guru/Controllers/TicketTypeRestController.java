package ticketguru.guru.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
}
