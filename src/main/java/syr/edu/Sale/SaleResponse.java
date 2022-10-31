package syr.edu.Sale;

public class SaleResponse {
    String status;

    public SaleResponse(String s){
        status = s;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
