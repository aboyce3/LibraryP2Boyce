package syr.edu.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private List<Book> owned;

    public User(String user){
        userName = user;
        initUser();
    }

    public String getUserName(){return userName;}
    public List<Book> getOwned(){return owned;}

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void addOwned(Book b){
        Book temp = b;
        Statement statement = null;
        ResultSet results = null;
        Connection con;
        for(int i = 0; i < owned.size(); i++){
            if(owned.get(i).getId().equals(b.getId())){
                owned.get(i).setStock(owned.get(i).getStock() + 1);
                String update = "UPDATE BookStore.userOwned Stock=" + owned.get(i).getStock() + " WHERE ID='" + userName
                        + " WHERE ID='" + userName + "' AND bookID=" + b.getId() + ";";
                execQuery(update);
                return;
            }
        }
        String update = "INSERT INTO BookStore.userOwned (bookID, userName, userAmount) VALUES (" + b.getId() + ", " + userName + ", " + 1 + ")";
        execQuery(update);
        temp.setStock(1);
        owned.add(temp);
    }

    public void removeOwned(Book b){
        for(int i = 0; i < owned.size(); i++){
            if(owned.get(i).getId().equals(b.getId())){
                if(owned.get(i).getStock()-1 > 0){
                    String downsize = "UPDATE BookStore.userOwned Stock=" + (owned.get(i).getStock()-1) + " WHERE ID='" +
                            userName + "' AND bookID=" + b.getId() + ";";
                    execQuery(downsize);
                    owned.get(i).setStock(owned.get(i).getStock()-1);
                    return;
                } else if(owned.get(i).getStock() -1 == 0){
                    String remove = "DELETE FROM BookStore.userOwned WHERE bookID=" + b.getId() + " AND userName ='" +
                            userName + "';";
                    execQuery(remove);
                    owned.remove(i);
                    return;
                }
            }
        }
    }

    public void initUser(){
        List<Book> temp = new ArrayList<>();
        List<Book> tempSell = new ArrayList<>();
        Connection con = null;
        String getOwned   = "SELECT Books.*, userAmount FROM BookStore.Books JOIN BookStore.userOwned ON ID=bookID " +
                "WHERE userName='" + this.getUserName() +"';";
        Statement statement = null;
        ResultSet results = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            statement = con.createStatement();
            results = statement.executeQuery(getOwned);
            while (results.next()) {
                //String isbn, List<String> Authors, String title, String edition, double price, int stock
                String id = results.getString("bookID");
                String ISBN = results.getString("ISBN");
                List<String> authors = List.of(results.getString("Authors").split(","));
                String title = results.getString("Title");
                String edition = results.getString("Edition");
                double price = results.getDouble("Price");
                int stock = results.getInt("userAmount");
                temp.add(new Book(id, ISBN, authors, title, edition, price, stock));
            }
            this.owned = temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execQuery(String query){
        Statement statement = null;
        ResultSet results = null;
        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasBook(String id){
        for(Book book : owned){
            if(book.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
}