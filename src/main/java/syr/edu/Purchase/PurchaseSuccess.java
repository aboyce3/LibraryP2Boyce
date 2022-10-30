package syr.edu.Purchase;

public class PurchaseSuccess extends PurchaseResponse {

    public PurchaseSuccess(double original, double newPrice) {
        super("Success", newPrice);
    }

}
