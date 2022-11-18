package syr.edu.Models;

import syr.edu.Services.SQLServices;

import java.util.List;

public class User {
    private final String username;
    private final List<String> owned;
    private final SQLServices database;

    public User(String user) {
        username = user;
        database = new SQLServices();
        owned = database.initUser(username);
    }
    public boolean containsID(String id){
        for(String s : owned){
            if(s.equals(id)){
                return true;
            }
        }
        return false;
    }
    public void addOwned(String id) {
        String update = "INSERT INTO BookStore.owned (ID, username) " +
                    "VALUES ('" + id + "', '" + username + "')";
        database.execModification(update);
        owned.add(id);
    }

    public void removeOwned(String id) {
        if (containsID(id)) {
            database.sellID(id, username);
            owned.remove(id);
        }
    }
}