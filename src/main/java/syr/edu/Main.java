package syr.edu;

import java.util.ArrayList;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        Library library = new Library();
        ArrayList<String> authors1a = new ArrayList<>();
        ArrayList<String> authors1b = new ArrayList<>();
        authors1a.add("Danny Devito");
        authors1b.add("Andy Boyce");
        authors1b.add("Ryan Reynolds");
        library.addBook(new Book("1a", "iubkuhgbks", authors1a, "My Book", "1ST", 54.09));
        library.addBook(new Book("1b", "iubkuhgbks", authors1b, "My Book", "1ST", 35.00));
        get("/inventory", library::inventory);
    }
}