package syr.edu;

import syr.edu.Objects.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Driver {
    /**
     * Used to populate the database with books.
     **/
    public static void main(String[] args) {
        //String isbn, String, String title, String edition, double price, int stock
        List<Book> books = new ArrayList<>();

        Book wimpyKid = new Book("9780810993136", Collections.singletonList("Jeff Kinney"), "Diary of a wimpy kid", "1ST", 9.00, 12);
        Book wmlb = new Book("1580627560", Collections.singletonList("Sherry Argo"), "Why Men Love Bitches", "1ST", 14.25, 6);
        Book grp = new Book("0593198255", Collections.singletonList("Eliza Jane Brazier"), "Good rich people", "1ST", 16.89, 5);
        Book iewu = new Book("1501110365", Collections.singletonList("Colleen Hoover"), "It ends with us", "1ST", 16.99, 11);
        Book tsyk = new Book("1470855844", Collections.singletonList("Kate White"), "The secrets you keep: a novel", "2ND", 15.29, 7);
        Book twngo = new Book("9781399713740", Collections.singletonList("Lucy Score"), "Things we never got over", "1ST", 13.67, 3);
        Book tpwk = new Book("1638081131", Collections.singletonList("Allison Larkin"), "The people we keep", "3RD", 14.79, 0);

        books.add(wimpyKid);
        books.add(wmlb);
        books.add(grp);
        books.add(iewu);
        books.add(tsyk);
        books.add(twngo);
        books.add(tpwk);

        //System.out.println(books);

        Connection con;
        for (Book b : books) {
            String insert = "INSERT into Books " + "(ID, ISBN, Authors, Title, Edition, Stock, Price)"
                    + "VALUES " + "('" + b.getId() + "','"
                    + b.getIsbn() + "','"
                    + b.authorsToDB() + "','"
                    + b.getTitle() + "','"
                    + b.getEdition() + "','"
                    + b.getStock() + "','"
                    + b.getPrice() + "')";
            Statement statement = null;
            ResultSet result = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
                statement = con.createStatement();
                statement.executeUpdate(insert);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}
