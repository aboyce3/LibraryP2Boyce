package syr.edu.Services;

import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import com.google.common.io.Files;

import static spark.Spark.halt;

public class RegisterUser {

    public String register(Request request, Response response) throws IOException {
        if(request.session().attribute("uName") != null) response.redirect("/Home");
        response.type("text/html");
        String path = "/Users/andrewboyce/eclipse-workspace/LibraryP2Boyce/src/main/resources/Public/Register.html";
        String content = Files.asCharSource(new File(path), StandardCharsets.UTF_8).read();
        return content;
    }

    public String AccountCreation(Request request, Response response){
        if(request.session().attribute("uName") != null){
            response.redirect("/Home");
            return "";
        }
        String userName = request.queryParams("username");
        String password = request.queryParams("password");
        String password2 = request.queryParams("confirm_password");
        Connection con;
        String insert = "INSERT into users " + "(username, password)" + "VALUES " + "('" + userName + "','"
                + password + "')";
        String lookup = "SELECT * FROM BookStore.users WHERE username = '" + userName + "'";
        Statement statement = null;
        ResultSet result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            statement = con.createStatement();
            result = statement.executeQuery(lookup);
            while (result.next()) {
                String uName = result.getString("userName");
                if (userName.contentEquals(uName))
                    response.redirect("/CreateAnAccount");
                return "";
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        response.type("text/html");
        if (userName == null || "".equals(userName) || password == null || "".equals(password) || password2 == null
                || "".equals(password2)) {
            response.redirect("/CreateAnAccount");
        } else if (password == null || "".equals(password) || !password.equals(password2)) {
            response.redirect("/CreateAnAccount");
        } else {
            try {
                if (statement != null) {
                    statement.executeUpdate(insert);
                    request.session().attribute("userName", userName);
                    request.session().maxInactiveInterval(9999);
                    response.redirect("/Home");
                    return "";
                } else
                    response.redirect("/CreateAnAccount");
                return "";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        response.redirect("/CreateAnAccount");
        return "";
    }

}
