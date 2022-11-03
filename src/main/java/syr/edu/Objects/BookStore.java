package syr.edu.Objects;

import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import syr.edu.Purchase.PurchaseFailure;
import syr.edu.Purchase.PurchaseSuccess;
import syr.edu.Sale.SaleFailure;
import syr.edu.Sale.SaleSuccess;
import syr.edu.Services.SQLServices;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookStore {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    SQLServices database;
    private List<Book> books;
    private User currentUser = null;

    public BookStore() {
        this.books = new ArrayList<>();
        database = new SQLServices();
        this.books = database.execSelect("SELECT * FROM Books;");
    }

    public String inventory(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(books);
    }

    public String buy(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        int newStock;
        double newPrice, oldPrice;
        String id = request.params(":id");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            String bookID = books.get(i).getId();
            if (bookID.equals(id) && book.getStock() > 0) {
                oldPrice = book.getPrice();
                newPrice = Double.parseDouble(df.format(book.getPrice() * .9));
                newStock = book.getStock() - 1;
                book.setPrice(newPrice);
                book.setStock(newStock);
                books.set(i, book);
                database.updateBook(book.getIsbn(), newPrice, book.getStock());
                currentUser.addOwned(books.get(i).getId());
                return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseSuccess(oldPrice, newPrice));
            }
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(new PurchaseFailure());
    }

    public String sellID(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        String id = request.params(":id");
        for (Book book : books)
            if (book.getId().equals(id) && currentUser.getOwned().containsKey(id)) {
                book.setStock(book.getStock() + 1);
                database.updateBook(book.getIsbn(), book.getPrice(), book.getStock());
                currentUser.removeOwned(id);
                return new GsonBuilder().setPrettyPrinting().create().toJson(new SaleSuccess());
            }
        return new GsonBuilder().setPrettyPrinting().create().toJson(new SaleFailure());
    }

    public String sellISBN(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        double price;
        int newStock;
        String isbn = request.params(":isbn");
        for (int i = 0; i < books.size(); i++)
            if (books.get(i).getIsbn().equals(isbn)) {
                price = books.get(i).getPrice();
                newStock = books.get(i).getStock() + 1;
                database.updateBook(isbn, price, newStock);
                return new GsonBuilder().setPrettyPrinting().create().toJson(new SaleSuccess());
            }

        return new GsonBuilder().setPrettyPrinting().create().toJson(new SaleFailure());
    }

    public boolean checkLogin(Request request) {
        if (request.cookies().containsKey("uName")) {
            currentUser = new User(request.cookies().get("uName"));
            return false;
        }
        if (request.session().attribute("uName") == null) {
            return true;
        } else {
            currentUser = new User(request.session().attribute("uName").toString());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                '}';
    }
}
