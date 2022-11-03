package syr.edu.Objects;

import syr.edu.Services.SQLServices;

import java.util.HashMap;

public class User {
    private final String username;
    private final HashMap<String, Integer> owned;
    private final SQLServices database;

    public User(String user) {
        username = user;
        database = new SQLServices();
        owned = database.initUser(username);
    }

    public String getUserName() {
        return username;
    }

    public HashMap<String, Integer> getOwned() {
        return owned;
    }

    public void addOwned(String id) {
        String update;
        if (owned.containsKey(id)) {
            owned.replace(id, owned.get(id) + 1);
            update = "UPDATE BookStore.userOwned SET userAmount='" + owned.get(id)
                    + "' WHERE userName='" + username + "' AND bookID='" + id + "';";
            database.execUpdateInsert(update);
        } else {
            update = "INSERT INTO BookStore.userOwned (bookID, userName, userAmount) " +
                    "VALUES ('" + id + "', '" + username + "', '" + 1 + "')";
            database.execUpdateInsert(update);
            owned.put(id, 1);
        }
    }

    public void removeOwned(String id) {
        if (owned.containsKey(id)) {
            if (owned.get(id) > 0) {
                String downsize = "UPDATE BookStore.userOwned userAmount=" + (owned.get(id) - 1) + " WHERE ID='" +
                        username + "' AND bookID=" + id + ";";
                database.execUpdateInsert(downsize);
                owned.replace(id, owned.get(id) - 1);
            } else if (owned.get(id) - 1 == 0) {
                String remove = "DELETE FROM BookStore.userOwned WHERE bookID=" + id + " AND userName ='" +
                        username + "';";
                database.execUpdateInsert(remove);
                owned.remove(id);
            }
        }
    }
}