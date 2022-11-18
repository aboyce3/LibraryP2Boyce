package syr.edu.Models;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Book {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private String id;
    private String isbn;
    private List<String> authors;
    private String title;
    private String edition;
    private double price;
    private String date;


    public Book(String isbn, List<String> authors, String title, String edition, double price, String date) {
        this.isbn = isbn;
        this.authors = authors;
        this.title = title;
        this.edition = edition;
        this.price = Double.parseDouble(df.format(price));
        this.id = UUID.randomUUID().toString();
        this.date = date;
    }

    public Book(String id, String isbn, List<String> authors, String title, String edition, double price, String date) {
        this.id = id;
        this.isbn = isbn;
        this.authors = authors;
        this.title = title;
        this.edition = edition;
        this.price = Double.parseDouble(df.format(price));
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }


    public String getIsbn() {
        return isbn;
    }


    public List<String> getAuthors() {
        return authors;
    }


    public String getTitle() {
        return title;
    }


    public String getEdition() {
        return edition;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = Double.parseDouble(df.format(price));
    }

    public String authorsToDB() {
        String result = "";
        if (authors.isEmpty()) {
            return "";
        }
        for (String s : authors) {
            result += s + ",";
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getIsbn(),
                book.getIsbn()) && Objects.equals(getAuthors(), book.getAuthors()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getEdition(), book.getEdition()) &&
                Objects.equals(getPrice(), book.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getAuthors(), getTitle(), getEdition(), getPrice());
    }

}
