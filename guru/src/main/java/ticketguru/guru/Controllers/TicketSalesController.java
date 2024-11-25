package ticketguru.guru.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ticketguru.guru.Services.TicketService;
import ticketguru.guru.dto.SaleRequest;
import ticketguru.guru.dto.SaleResponse;

@RequestMapping("/tickets")
public class TicketSalesController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/sell")
    public ResponseEntity<SaleResponse> sellTicket(@RequestBody SaleRequest saleRequest) throws Exception {
        SaleResponse response = ticketService.processSale(saleRequest);
        return ResponseEntity.ok(response);
    }
}
