package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.*;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String isbn;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_store", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    private Set<Store> stores;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private Set<Author> authors;

    public Book() {
        this.stores = new HashSet<>();
        this.authors = new HashSet<>();
    }

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
        this.stores = new HashSet<>();
        this.authors = new HashSet<>();
    }

    public void addStore(Store store) {
        this.stores.add(store);
        store.getBooks().add(this);
    }

    public void removeStore(Store store) {
        this.stores.remove(store);
        store.getBooks().remove(this);
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    @Override
    public int hashCode() {
        return this.isbn.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(isbn, book.isbn);
    }
}