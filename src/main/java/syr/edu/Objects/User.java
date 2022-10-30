package syr.edu.Objects;

import syr.edu.Objects.Book;

import java.util.ArrayList;

public class User {
    private String userName;
    private String email;
    private ArrayList<Book> owned, selling;

    public User(String user, String email){
        this.email = email;
        userName = user;
        owned = new ArrayList<>();
        selling = new ArrayList<>();
    }

    public String getEmail(){return email;}
    public String getUserName(){return userName;}
    public ArrayList<Book> getOwned(){return owned;}
    public ArrayList<Book> getSelling(){return selling;}

    public void setEmail(String email){this.email = email;}
    public void setUserName(String userName){this.userName = userName;}
    public void addOwned(Book b){owned.add(b);}
    public void addSelling(Book b){selling.add(b);}

    public void removeOwned(Book b){
        for(int i = 0; i < owned.size(); i++){
            if(owned.get(i).equals(b)){
                owned.remove(i);
                return;
            }
        }
    }
    public void removeSelling(Book b) {
        for (int i = 0; i < selling.size(); i++) {
            if (selling.get(i).equals(b)) {
                selling.remove(i);
                return;
            }
        }
    }
}