package syr.edu.Objects;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Book {
    private String id;
    private String isbn;
    private List<String> authors;
    private String title;
    private String edition;
    private double price;
    private int stock;

    public Book(String isbn, List<String> authors, String title, String edition, double price, int stock){
        this.isbn = isbn;
        this.authors = authors;
        this.title = title;
        this.edition = edition;
        this.price = Math.round(price*100.0)/100.0;
        this.stock = stock;
        this.id = String.valueOf(UUID.randomUUID().toString());
    }
    public Book(String isbn, String authors, String title, String edition, String s, double price, int stock){
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
        this.authors = List.of(authors.split(","));
        this.price = Math.round(price*100.0)/100.0;
        this.stock = stock;
        this.id = String.valueOf(UUID.randomUUID().toString());
    }

    public Book(String id, String isbn, List<String> authors, String title, String edition, double price, int stock) {
        this.id = id;
        this.isbn = isbn;
        this.authors = authors;
        this.title = title;
        this.edition = edition;
        this.price = price;
        this.stock = stock;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if(stock < 0){
            return;
        }
        this.stock = stock;
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setPrice(double price) {
        this.price = Math.round(price*100.0)/100.0;
    }

    public String authorsToDB(){
        String result = "";
        if(authors.isEmpty()){
            return "";
        }
        for(String s :authors){
            result += s + ",";
        }
        return result.substring(0, result.length()-2);
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
