package syr.edu.Services;

import com.google.common.io.Files;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RegisterServices {

    SQLServices database;

    public RegisterServices() {
        database = new SQLServices();
    }

    public String register(Request request, Response response) throws IOException {
        if (request.session().attribute("uName") != null) response.redirect("/Home");
        response.type("text/html");
        String path = "/Users/andrewboyce/eclipse-workspace/LibraryP2Boyce/src/main/resources/Public/Register.html";
        String content = Files.asCharSource(new File(path), StandardCharsets.UTF_8).read();
        return content;
    }

    public String AccountCreation(Request request, Response response) {
        if (request.session().attribute("uName") != null) {
            response.redirect("/Home");
            return "";
        }
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String password2 = request.queryParams("confirm_password");
        String insert = "INSERT into users " + "(username, password)" + "VALUES " + "('" + username + "','"
                + password + "')";
        String lookup = "SELECT * FROM BookStore.users WHERE username = '" + username + "'";
        if (password == null || "".equals(password) || !password.equals(password2)) {
            response.redirect("/CreateAnAccount");
            return "";
        }
        if (database.userCanRegister(lookup, username) && database.execModification(insert)) {
            response.type("text/html");
            request.session().attribute("uName", username);
            request.session().maxInactiveInterval(9999);
            response.redirect("/Home");
            return "";
        }
        response.redirect("/CreateAnAccount");
        return "";
    }

}
