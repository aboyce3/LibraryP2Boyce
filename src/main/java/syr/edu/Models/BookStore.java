package syr.edu.Models;
import syr.edu.Services.SQLServices;

import java.util.ArrayList;
import java.util.List;

public class BookStore {

    SQLServices database;
    private List<Book> books;

    public BookStore() {
        this.books = new ArrayList<>();
        database = new SQLServices();
        this.books = database.execSelect("SELECT * FROM Books WHERE ID NOT IN (SELECT ID FROM owned);");
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(String id){
        for(int i = 0; i < books.size(); i++)
            if(books.get(i).getId().equals(id)){
                books.remove(i);
                return;
            }
    }

    public boolean updateBookPrices(String ID, double price){
        for(int i = 0; i < books.size(); i++)
            if(books.get(i).getId().equals(ID))
                books.get(i).setPrice(price);
        return database.updateBook(ID, price);
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                '}';
    }
}
