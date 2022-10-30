package syr.edu.Objects;

import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import syr.edu.Purchase.PurchaseFailure;
import syr.edu.Purchase.PurchaseSuccess;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookStore {

    private final List<Book> books;

    public BookStore(ArrayList<Book> books){
        this.books = books;
    }

    public BookStore(){
        this.books = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public List<Book> getBooks(){
        return this.books;
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                '}';
    }

    public String inventory(Request request, Response response) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(books);
    }

    public String buy(Request request, Response response) {
        double newPrice = 0.0;
        double oldPrice = 0.0;
        String id = request.params(":id");
        for(int i = 0; i < books.size(); i++){
            Book b = books.get(i);
            if(b.getId().equals(id)){
                oldPrice = b.getPrice();
                newPrice = Double.parseDouble(df.format(b.getPrice() * .9));
                b.setPrice(newPrice);
                books.set(i, b);
                return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseSuccess(oldPrice, newPrice));
            }
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseFailure());
    }

    public String sell(Request request, Response response) {
        double newPrice = 0.0;
        double oldPrice = 0.0;
        String id = request.params(":id");
        for(int i = 0; i < books.size(); i++){
            Book b = books.get(i);
            if(b.getId().equals(id)){
                oldPrice = b.getPrice();
                newPrice = Double.parseDouble(df.format(b.getPrice() * .9));
                b.setPrice(newPrice);
                books.set(i, b);
                return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseSuccess(oldPrice, newPrice));
            }
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseFailure());
    }

    private void initLibrary(){

    }
}
