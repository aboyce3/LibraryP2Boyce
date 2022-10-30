package syr.edu.Objects;

import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import syr.edu.Purchase.PurchaseFailure;
import syr.edu.Purchase.PurchaseSuccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookStore {

    private List<Book> books;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public BookStore(ArrayList<Book> books){
        this.books = books;
    }

    public BookStore(){
        this.books = new ArrayList<>();
        initLibrary();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public List<Book> getBooks(){
        return this.books;
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
            if(b.getId().equals(id) && b.getStock() > 0){
                oldPrice = b.getPrice();
                newPrice = Double.parseDouble(df.format(b.getPrice() * .9));
                b.setPrice(newPrice);
                b.setStock(b.getStock() - 1);
                books.set(i, b);
                updateBookPrice(b.getId(), newPrice);
                updateBookStock(b.getId(), b.getStock()-1);
                return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseSuccess(oldPrice, newPrice));
            }
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseFailure());
    }

    public String sellID(Request request, Response response) {
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

    public String sellISBN(Request request, Response response) {
        double newPrice = 0.0;
        double oldPrice = 0.0;
        String isbn = request.params(":isbn");
        for(int i = 0; i < books.size(); i++){
            Book b = books.get(i);
            if(b.getIsbn().equals(isbn)){
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
        List<Book> temp = new ArrayList<>();
        Connection con = null;
        String lookup = "SELECT * FROM Books;";
        Statement statement = null;
        ResultSet results = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            statement = con.createStatement();
            results = statement.executeQuery(lookup);
            while (results.next()) {
                //String isbn, List<String> Authors, String title, String edition, double price, int stock
                String ISBN = results.getString("ISBN");
                List<String> authors = List.of(results.getString("Authors").split(","));
                String title = results.getString("Title");
                String edition = results.getString("Edition");
                double price = results.getDouble("Price");
                int stock = results.getInt("Stock");
                temp.add(new Book(ISBN, authors, title, edition, price, stock));
            }
            this.books = temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                '}';
    }

    private void updateBookPrice(String Id, double price){
        String lookup = "UPDATE * FROM Books;";
        Statement statement = null;
        ResultSet results = null;
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            statement = con.createStatement();
            results = statement.executeQuery(lookup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decrementUserInventory(String username){

    }

    private void updateBookStock(String Id, int stock){

    }
}
