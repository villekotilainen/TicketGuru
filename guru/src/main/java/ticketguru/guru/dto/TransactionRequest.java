package ticketguru.guru.dto;

import java.util.List;

public class TransactionRequest {
    private List<Long> ticketTypeIds; // IDs of the ticket types being processed
    private Long userId;             // ID of the user initiating the transaction

    // Getters and setters
    public List<Long> getTicketTypeIds() {
        return ticketTypeIds;
    }

    public void setTicketTypeIds(List<Long> ticketTypeIds) {
        this.ticketTypeIds = ticketTypeIds; // Correctly assigning the parameter
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

