package syr.edu.Services;

import com.google.common.io.Files;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class LoginUser {
    public String login(Request request, Response response) throws IOException {
        if(request.session().attribute("uName") != null){
            response.redirect("/Home");
            return "";
        }
        response.type("text/html");
        String path = "/Users/andrewboyce/eclipse-workspace/LibraryP2Boyce/src/main/resources/Public/Login.html";
        String content = Files.asCharSource(new File(path), StandardCharsets.UTF_8).read();
        return content;
    }

    public String validateLogin(Request request, Response response){
        if(request.session().attribute("uName") != null){
            response.redirect("/Home");
            return "";
        }
        String userName = request.queryParams("userName");
        String password = request.queryParams("password");
        String lookup = "SELECT * FROM BookStore.users WHERE username = '" + userName + "' AND password = '"
                + password + "';";
        Connection con = null;
        Statement statement = null;
        ResultSet results = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            statement = con.createStatement();
            results = statement.executeQuery(lookup);
            while (results.next()) {
                String uName = results.getString("username");
                String pass = results.getString("password");
                if (userName.contentEquals(uName) && password.contentEquals(pass)) {
                    request.session().attribute("username", userName);
                    request.session().maxInactiveInterval(99999);;
                    response.redirect("/Hub");
                    return "";
                }
            }
            response.redirect("/Login");
            return "";
        } catch (SQLException | ClassNotFoundException e) {
            response.redirect("/Login");
            return "";
        }
    }
}
