package syr.edu.Services;

import syr.edu.Models.Book;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public boolean execModification(String query) {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException s) {
            return false;
        }
    }

    public double priceLookup(String id) {
        try {
            String query = "SELECT Price FROM Books WHERE ID='" + id + "';";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);
            List<Book> temp = new ArrayList<>();
            while (results.next()) {
                return results.getDouble("Price");
            }
            return 0.0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
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
                String date = results.getString("Date");
                temp.add(new Book(id, ISBN, authors, title, edition, price, date));
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

    public boolean updateBook(String ID, double price) {
        String update = "UPDATE BookStore.Books SET Price='" + price + "'" + " WHERE ID='" + ID + "';";
        return execModification(update);
    }

    public boolean updateBookDate(String ID){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDay = LocalDate.parse(myFormatObj.format(currentDate));
        String update = "UPDATE BookStore.Books SET Date='" + currentDay + "'" + " WHERE ID='" + ID + "';";
        return execModification(update);
    }

    public boolean sellISBN(Book b){
        String insert = "INSERT into BookStore.Books " + "(ID, ISBN, Authors, Title, Edition, Price, Date)"
                + "VALUES " + "('" + b.getId() + "','"
                + b.getIsbn() + "','"
                + b.authorsToDB() + "','"
                + b.getTitle() + "','"
                + b.getEdition() + "','"
                + b.getPrice() + "','"
                + b.getDate() + "')";
        return execModification(insert);
    }

    public boolean sellID(String id, String username){
        String sell = "DELETE FROM BookStore.owned WHERE ID='" + id + "' AND username='" + username +"';";
        return execModification(sell);
    }

    public List<String> initUser(String username) {
        List<String> idNums = new ArrayList<>();
        try {
            String getOwned = "SELECT * FROM BookStore.Books JOIN BookStore.owned ON Books.ID=owned.ID " +
                    "WHERE username='" + username + "';";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(getOwned);
            while (results.next()) {
                String id = results.getString("ID");
                idNums.add(id);
            }
            return idNums;
        } catch (SQLException e) {
            e.printStackTrace();
            return idNums;
        }
    }
}
