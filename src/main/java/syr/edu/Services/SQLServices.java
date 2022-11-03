package syr.edu.Services;

import syr.edu.Objects.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLServices {

    Connection con;

    public SQLServices() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean execUpdateInsert(String query) {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException s) {
            return false;
        }
    }

    public List<Book> execSelect(String query) {
        try {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);
            List<Book> temp = new ArrayList<>();
            while (results.next()) {
                String id = results.getString("ID");
                String ISBN = results.getString("ISBN");
                List<String> authors = List.of(results.getString("Authors").split(","));
                String title = results.getString("Title");
                String edition = results.getString("Edition");
                double price = results.getDouble("Price");
                int stock = results.getInt("Stock");
                temp.add(new Book(id, ISBN, authors, title, edition, price, stock));
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean userLoginLookup(String query, String username, String password) {
        try {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                String uName = results.getString("userName");
                String pass = results.getString("password");
                if (username.contentEquals(uName) && password.contentEquals(pass)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userCanRegister(String query, String username) {
        if (username == null || "".equals(username)) {
            return false;
        }
        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String uName = result.getString("userName");
                if (username.contentEquals(uName))
                    return false;
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateBook(String ISBN, double price, int stock) {
        String update = "UPDATE Books SET Price='" + price + "', Stock='" + stock + "' WHERE ISBN='" + ISBN + "'";
        return execUpdateInsert(update);
    }

    public HashMap<String, Integer> initUser(String username) {
        HashMap<String, Integer> temp = new HashMap<>();
        try {
            String getOwned = "SELECT * FROM BookStore.Books JOIN BookStore.userOwned ON ID=bookID " +
                    "WHERE userName='" + username + "';";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(getOwned);
            while (results.next()) {
                String id = results.getString("ID");
                int stock = results.getInt("userAmount");
                temp.put(id, stock);
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return temp;
        }
    }
}
