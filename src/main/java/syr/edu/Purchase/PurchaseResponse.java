package syr.edu.Purchase;

public class PurchaseResponse {
    private String status;
    private double newPrice;

    public PurchaseResponse(String status, double newPrice) {
        this.status = status;
        this.newPrice = newPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
