package ticketguru.guru.dto;

public class TicketTypeSalesDTO {

    private String ticketType;      // The type of the ticket (e.g., "Adult", "Child")
    private Long totalTicketsSold; // Total number of tickets sold for this type
    private Double totalRevenue;   // Total revenue generated for this ticket type

    // Constructor
    public TicketTypeSalesDTO(String ticketType, Long totalTicketsSold, Double totalRevenue) {
        this.ticketType = ticketType;
        this.totalTicketsSold = totalTicketsSold;
        this.totalRevenue = totalRevenue;
    }

    // Getters and Setters
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Long getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(Long totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    // Override toString() for debugging
    @Override
    public String toString() {
        return "TicketTypeSalesDTO{" +
                "ticketType='" + ticketType + '\'' +
                ", totalTicketsSold=" + totalTicketsSold +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
