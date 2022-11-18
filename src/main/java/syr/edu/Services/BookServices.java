package syr.edu.Services;

import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import syr.edu.Models.Algorithm;
import syr.edu.Models.Book;
import syr.edu.Models.BookStore;
import syr.edu.Models.User;

import java.util.List;

import static spark.Spark.halt;
public class BookServices {

    private User currentUser = null;
    SQLServices database;
    BookStore store;

    public BookServices(){
        database = new SQLServices();
        store = new BookStore();
    }

    public String inventory(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(store.getBooks());
    }

    public String buy(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        String id = request.params(":id");
        List<Book> tempBooks = store.getBooks();
        for (int i = 0; i < tempBooks.size(); i++) {
            if (tempBooks.get(i).getId().equals(id)) {
                Book book = store.getBooks().get(i);
                JSONObject json = new JSONObject();
                Algorithm discount = Algorithm.getInstance(book.getDate(), book.getPrice());
                double originalPrice = discount.getOriginalPrice();
                double newPrice = discount.getNewPrice();
                store.updateBookPrices(book.getId(), newPrice);
                store.removeBook(id);
                currentUser.addOwned(book.getId());
                json.put("status", "Successful");
                json.put("oldPrice", originalPrice);
                json.put("newPrice", newPrice);
                return new GsonBuilder().setPrettyPrinting().create().toJson(json.toString());
            }
        }
        halt(401);
        return new GsonBuilder().setPrettyPrinting().create().toJson("");
    }

    public String sellID(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        String id = request.params(":id");
        if (currentUser.containsID(id)) {
            double price = database.priceLookup(id);
            database.updateBookDate(id);
            currentUser.removeOwned(request.params(":id"));
            JSONObject json = new JSONObject();
            json.put("status", "Successful");
            json.put("price", price);
            return new GsonBuilder().setPrettyPrinting().create().toJson(json.toString());
        }

        halt(401);
        return new GsonBuilder().setPrettyPrinting().create().toJson("");
    }

    public String sellISBN(Request request, Response response) {
        if (checkLogin(request)) {
            response.redirect("/Login");
            return "";
        }
        for (int i = 0; i < store.getBooks().size(); i++)
            if (store.getBooks().get(i).getIsbn().equals(request.params(":isbn"))) {
                Book currentBook = store.getBooks().get(i);
                String currIsbn = currentBook.getIsbn();
                String currEd = currentBook.getEdition();
                double currPrice = currentBook.getPrice();
                String currTitle = currentBook.getTitle();
                String currDate = currentBook.getDate();
                List<String> currAuthors = currentBook.getAuthors();
                Book newBook = new Book(currIsbn, currAuthors, currTitle, currEd, currPrice, currDate);
                database.sellISBN(newBook);
                store.addBook(newBook);
                database.updateBookDate(newBook.getId());
                JSONObject json = new JSONObject();
                json.put("status", "Successful");
                json.put("price", currPrice);
                return new GsonBuilder().setPrettyPrinting().create().toJson(json.toString());
            }
        halt(401);
        return new GsonBuilder().setPrettyPrinting().create().toJson("");
    }

    public boolean checkLogin(Request request) {
        if (request.cookies().containsKey("uName")) {
            currentUser = new User(request.cookies().get("uName"));
            return false;
        }
        else if (request.session().attribute("uName") != null) {
            currentUser = new User(request.session().attribute("uName").toString());
            return false;
        } else {
            return true;
        }
    }
}
