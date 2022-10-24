package syr.edu;

public class Transaction{
    private String status;
    private double originalPrice;
    private double newPrice;

    public Transaction(String status, double originalPrice, double newPrice) {
        this.status = status;
        this.originalPrice = originalPrice;
        this.newPrice = newPrice;
    }

    public Transaction(String status) {
        this.status = status;
        this.originalPrice = 0.0;
        this.newPrice = 0.0;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
