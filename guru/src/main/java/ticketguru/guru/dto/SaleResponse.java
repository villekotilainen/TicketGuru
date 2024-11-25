package ticketguru.guru.dto;


public class SaleResponse {

    private double totalAmount; // Kokonaissumma, joka on maksettava
    private String status; // Myynnin tila (esim. "SUCCESS" tai "FAILURE")
    private String message; // Viesti tai lisätiedot myynnin tilasta

    // Getterit ja setterit
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
