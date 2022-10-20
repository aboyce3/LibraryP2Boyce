package syr.edu;

import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library(ArrayList<Book> books){
        this.books = books;
    }

    public Library(){
        this.books = new ArrayList<Book>();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public List<Book> getBooks(){
        return this.books;
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                '}';
    }

    public String inventory(Request request, Response response) {
        response.type("application/json");
        return new GsonBuilder().setPrettyPrinting().create().toJson(books);
    }
}
