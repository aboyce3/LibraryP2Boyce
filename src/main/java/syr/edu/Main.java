package syr.edu;

import com.google.common.io.Files;
import syr.edu.Services.BookServices;
import syr.edu.Services.LoginServices;
import syr.edu.Services.RegisterServices;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        RegisterServices register = new RegisterServices();
        LoginServices login = new LoginServices();
        BookServices bookServices = new BookServices();

        //Get Requests
        get("/Login", "application/html", login::login);
        get("/ValidateLogin", "application/html", login::validateLogin);
        get("/CreateAnAccount", "application/html", register::register);
        get("/Inventory", "application/json", bookServices::inventory);
        get("/Home", "application/html,", (request, response) -> {
            if (request.session().attribute("uName") == null && !request.cookies().containsKey("uName")) {
                response.redirect("/Login");
                return "";
            }
            response.type("text/html");
            String path = "/Users/andrewboyce/eclipse-workspace/LibraryP2Boyce/src/main/resources/Public/Home.html";
            String content = Files.asCharSource(new File(path), StandardCharsets.UTF_8).read();
            return content;
        });

        //Post Requests
        post("/AccountCreation", "application/html", register::AccountCreation);
        post("/Sell/:id", "application/json", bookServices::sellID);
        post("/Buy/:id", "application/json", bookServices::buy);

        //Put Requests
        put("/Sell/:isbn", "application/json", bookServices::sellISBN);
    }
}