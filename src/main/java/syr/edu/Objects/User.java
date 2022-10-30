package syr.edu.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private List<Book> owned, selling;

    public User(String user){
        userName = user;
        initUser();
    }

    public String getUserName(){return userName;}
    public List<Book> getOwned(){return owned;}
    public List<Book> getSelling(){return selling;}

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void addOwned(Book b){
        Book temp = b;
        for(int i = 0; i < owned.size(); i++){
            if(owned.get(i).getId().equals(b.getId())){
                owned.get(i).setStock(owned.get(i).getStock() + 1);
                return;
            }
        }
        temp.setStock(1);
        owned.add(temp);
    }

    public void addSelling(Book b){
        Book temp = b;
        for(int i = 0; i < selling.size(); i++){
            if(selling.get(i).getId().equals(b.getId())){
                selling.get(i).setStock(selling.get(i).getStock() + 1);
                return;
            }
        }
        temp.setStock(1);
        selling.add(temp);
    }

    public void removeOwned(Book b){
        for(int i = 0; i < owned.size(); i++){
            if(owned.get(i).getId().equals(b.getId())){
                if(owned.get(i).getStock() > 0){
                    owned.get(i).setStock(owned.get(i).getStock()-1);
                    return;
                } else if(owned.get(i).getStock() == 0){
                    owned.remove(i);
                    return;
                }
            }
        }
    }

    public void removeSelling(Book b) {
        for(int i = 0; i < selling.size(); i++){
            if(selling.get(i).getId().equals(b.getId())){
                if(selling.get(i).getStock() > 0){
                    selling.get(i).setStock(selling.get(i).getStock()-1);
                    return;
                } else if(selling.get(i).getStock() == 0){
                    selling.remove(i);
                    return;
                }
            }
        }
    }

    public void initUser(){
        List<Book> temp = new ArrayList<>();
        List<Book> tempSell = new ArrayList<>();
        Connection con = null;
        String getOwned = "SELECT * FROM Books JOIN userOwned ON ID=bookID WHERE userName='"+ this.getUserName() +"';";
        String getSelling = "SELECT * FROM Books JOIN userStock ON ID=bookID WHERE userName='"+ this.getUserName() +"';";
        Statement statement = null;
        ResultSet results = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "password");
            statement = con.createStatement();
            results = statement.executeQuery(getOwned);
            while (results.next()) {
                //String isbn, List<String> Authors, String title, String edition, double price, int stock
                String ISBN = results.getString("ISBN");
                List<String> authors = List.of(results.getString("Authors").split(","));
                String title = results.getString("Title");
                String edition = results.getString("Edition");
                double price = results.getDouble("Price");
                int stock = results.getInt("Stock");
                temp.add(new Book(ISBN, authors, title, edition, price, stock));
            }
            this.owned = temp;
            results = statement.executeQuery(getSelling);
            while (results.next()) {
                //String isbn, List<String> Authors, String title, String edition, double price, int stock
                String ISBN = results.getString("ISBN");
                List<String> authors = List.of(results.getString("Authors").split(","));
                String title = results.getString("Title");
                String edition = results.getString("Edition");
                double price = results.getDouble("Price");
                int stock = results.getInt("Stock");
                tempSell.add(new Book(ISBN, authors, title, edition, price, stock));
            }
            this.selling = tempSell;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}