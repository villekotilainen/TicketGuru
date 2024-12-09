package ticketguru.guru.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Repositories.TicketRepository;
import ticketguru.guru.Repositories.TicketTypeRepository;
import ticketguru.guru.dto.SaleRequest;
import ticketguru.guru.dto.SaleResponse;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    // Käsittele lipunmyynti
    @Transactional
    public SaleResponse processSale(SaleRequest saleRequest) throws Exception {
        // Tarkista saatavuus
        boolean isAvailable = checkAvailability(saleRequest.getTicketTypes());
        if (!isAvailable) {
            throw new Exception("Liput eivät ole saatavilla.");
        }

        // Laske kokonaissumma
        double totalAmount = calculateTotalAmount(saleRequest.getTicketTypes());

        // Luo ja tallenna transaktio (tai muu tarvittava entiteetti)
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setTotalAmount(totalAmount);
        saleResponse.setStatus("SUCCESS");

        // Voit lisätä myös logiikkaa esim. maksujen käsittelyyn tai vahvistukseen
        return saleResponse;
    }

    // Tarkista lipputyyppien saatavuus
    private boolean checkAvailability(List<String> ticketTypeNames) {
        for (String ticketTypeName : ticketTypeNames) {
            TicketType ticketType = ticketTypeRepository.findByTypeName(ticketTypeName)
                    .orElseThrow(() -> new IllegalArgumentException("Virheellinen lipputyyppi: " + ticketTypeName));

            // Check if there are available tickets of this type (assuming totalCount is the
            // availability)
            if (ticketType.getTotalCount() <= 0) {
                return false; // Jos jokin lipputyyppi ei ole saatavilla
            }
        }
        return true;
    }

    // Laske kokonaissumma lipputyyppien perusteella
    private double calculateTotalAmount(List<String> ticketTypes) {
        double totalAmount = 0;
        for (String ticketType : ticketTypes) {
            totalAmount += getTicketPrice(ticketType); // Hae hinta lipputyypin perusteella
        }
        return totalAmount;
    }

    // Hae lipun hinta lipputyypin perusteella
    private double getTicketPrice(String ticketType) {
        TicketType type = ticketTypeRepository.findByTypeName(ticketType)
                .orElseThrow(() -> new IllegalArgumentException("Tuntematon lipputyyppi: " + ticketType));
        return type.getTicketPrice();
    }

    @Transactional
    public void processSale(Long ticketTypeId, int quantity) throws Exception {
        // Fetch TicketType
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid TicketType ID"));

        // Check availability
        if (!ticketType.hasSufficientTickets(quantity)) {
            throw new Exception("Not enough tickets available for type: " + ticketType.getTypeName());
        }

        // Deduct tickets
        ticketType.setTotalCount(ticketType.getTotalCount() - quantity);
        ticketTypeRepository.save(ticketType);

        // Generate tickets
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketType(ticketType);
            ticket.setHashcode(UUID.randomUUID().toString());
            ticketRepository.save(ticket);
        }
    }

}
