package syr.edu;

import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books;

    public Library(ArrayList<Book> books){
        this.books = books;
    }

    public Library(){
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
        response.type("application/json");
        return new GsonBuilder().setPrettyPrinting().create().toJson(books);
    }

    public String buy(Request request, Response response) {
        response.type("application/json");
        String id = request.params(":id");
        for(int i = 0; i < books.size(); i++){
            Book b = books.get(i);
            if(b.getId().equals(id)){
                double oldPrice = b.getPrice();
                double newPrice = Double.parseDouble(df.format(b.getPrice() * .9));
                b.setPrice(newPrice);
                books.set(i, b);
                return new GsonBuilder().setPrettyPrinting().create().toJson(new Transaction("Successful", oldPrice, newPrice));
            }
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(new Transaction("Failure"));
    }
}
