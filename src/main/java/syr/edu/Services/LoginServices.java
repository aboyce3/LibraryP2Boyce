package syr.edu.Services;

import com.google.common.io.Files;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginServices {

    SQLServices database;

    public LoginServices() {
        database = new SQLServices();
    }

    public String login(Request request, Response response) throws IOException {
        if (request.session().attribute("uName") != null) {
            response.redirect("/Home");
            return "";
        }
        response.type("text/html");
        String path = "/Users/andrewboyce/eclipse-workspace/LibraryP2Boyce/src/main/resources/Public/Login.html";
        String content = Files.asCharSource(new File(path), StandardCharsets.UTF_8).read();
        return content;
    }

    public String validateLogin(Request request, Response response) {
        if (request.session().attribute("uName") != null) {
            response.redirect("/Home");
            return "";
        }
        String userName = request.queryParams("userName");
        String password = request.queryParams("password");
        String lookup = "SELECT * FROM BookStore.users WHERE username = '" + userName + "' AND password = '"
                + password + "';";
        if (database.userLoginLookup(lookup, userName, password)) {
            request.session().attribute("uName", userName);
            request.session().maxInactiveInterval(99999);
            response.redirect("/Home");
            return "";
        }
        response.redirect("/Login");
        return "";
    }
}
