package syr.edu;

import com.google.common.io.Files;
import syr.edu.Objects.BookStore;
import syr.edu.Services.LoginUser;
import syr.edu.Services.RegisterUser;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        RegisterUser register = new RegisterUser();
        BookStore bookStore = new BookStore();
        LoginUser login = new LoginUser();

        get("/Login", "application/html", login::login);
        get("/ValidateLogin", "application/html", login::validateLogin);
        get("/CreateAnAccount", "application/html", register::register);
        get("/Inventory", "application/json", bookStore::inventory);
        get("/Buy/:id", "application/json", bookStore::buy);

        get("/Logout", "application/html", (request, response) -> {
            request.session().invalidate();
            response.redirect("/Login");
            return "";
        });
        get("/Home", "application/html,", (request, response) -> {
            if(request.session().attribute("uName") != null) response.redirect("/Login");
            response.type("text/html");
            String path = "/Users/andrewboyce/eclipse-workspace/LibraryP2Boyce/src/main/resources/Public/Home.html";
            String content = Files.asCharSource(new File(path), StandardCharsets.UTF_8).read();
            return content;
        });

        post("/AccountCreation", "application/html", register::AccountCreation);
        post("/Sell/:id", "application/json", bookStore::sell);
    }
}

//        ArrayList<String> authors1a = new ArrayList<>();
//        ArrayList<String> authors1b = new ArrayList<>();
//        authors1a.add("Danny Devito");
//        authors1b.add("Andy Boyce");
//        authors1b.add("Ryan Reynolds");
//        library.addBook(new Book("1a", "iubkuhgbks", authors1a, "My Book", "1ST", 54.09));
//        library.addBook(new Book("1b", "iubkuhgbks", authors1b, "My Book", "1ST", 35.00));