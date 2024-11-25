package ticketguru.guru.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            TicketType ticketType = ticketTypeRepository.findByTypeName(ticketTypeName) // Use correct method from repository
                .orElseThrow(() -> new IllegalArgumentException("Virheellinen lipputyyppi: " + ticketTypeName));
            
            if (!ticketRepository.existsByTicketType(ticketType)) {
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
        switch (ticketType) {
            case "Aikuinen":
                return 15.00;
            case "Lapsi":
                return 7.50;
            case "Eläkeläinen":
                return 10.00;
            default:
                throw new IllegalArgumentException("Tuntematon lipputyyppi: " + ticketType);
        }
    }
}
