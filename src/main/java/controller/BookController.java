package controller;

import model.Author;
import model.Book;
import model.Business;
import model.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class BookController {
    public static Book get(Session session, Long id) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Retrieving Book ID: %d%n", id);
        System.out.println("--------------------------------------------------");

        Book book = session.get(Book.class, id);

        if (book == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Book not found");
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Book Retrieved Successfully!");
            System.out.printf("Book ID: %d%n", book.getId());
            System.out.printf("Book Title: %s%n", book.getTitle());
            System.out.printf("Book ISBN: %s%n", book.getIsbn());
            System.out.println("--------------------------------------------------");
        }
        return book;
    }

    public static Book persist(Session session, String title, String isbn) {
        return persist(session, new Book(title, isbn));
    }

    public static Book persist(Session session, Book book) {
        Transaction transaction = null;

        if (book == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Book is null!");
            System.out.println("--------------------------------------------------");
            return book;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.println("Adding a new Book");
            System.out.println("--------------------------------------------------");

            session.persist(book);
            transaction.commit();

            System.out.println("--------------------------------------------------");
            System.out.println("Book Added Successfully!");
            System.out.printf("Book ID: %d%n", book.getId());
            System.out.printf("Book Title: %s%n", book.getTitle());
            System.out.printf("Book ISBN: %s%n", book.getIsbn());
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Failed to Add Book!");
            System.out.println("--------------------------------------------------");
        }
        return book;
    }

    public static void delete(Session session, Long id) {
        Book book = session.get(Book.class, id);

        if (book == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Book not found!");
            System.out.println("--------------------------------------------------");
            return;
        }
        delete(session, book);
    }

    public static void delete(Session session, Book book) {
        Transaction transaction = null;

        if (book == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Book is null");
            System.out.println("--------------------------------------------------");
            return;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Deleting Book ID: %d%n", book.getId());
            System.out.println("--------------------------------------------------");

            session.remove(book);
            transaction.commit();
            session.evict(book);

            System.out.println("--------------------------------------------------");
            System.out.println("Book Deleted Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Delete Book Failed!");
            System.out.println("--------------------------------------------------");
        }
    }

    public static Book update(Session session, Book book) {
        Transaction transaction = null;
        Book updatedBook = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Updating Book ID: %d%n", book.getId());
            System.out.println("--------------------------------------------------");

            updatedBook = session.merge(book);
            transaction.commit();
            session.evict(book);

            System.out.println("--------------------------------------------------");
            System.out.println("Book Updated Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Update Book Failed!");
            System.out.println("--------------------------------------------------");
        }
        return updatedBook;
    }

    public static void showInfo(Book book) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Book Title: %s%n", book.getTitle());
        System.out.printf("Book ISBN: %s%n", book.getIsbn());

        System.out.println("--------------------");
        Set<Author> authors = book.getAuthors();
        System.out.printf("Number of Authors: %d%n", authors.size());

        for (Author author : authors) {
            System.out.println("--------------------");
            System.out.printf("Author Name: %s%n", author.getFullName());
        }

        System.out.println("--------------------");
        Set<Store> stores = book.getStores();
        System.out.printf("Sold in %d stores%n", stores.size());

        for (Store store : stores) {
            System.out.println("--------------------");
            System.out.printf("Store Name: %s%n", store.getName());
            System.out.printf("Store Address: %s%n", store.getAddress());

            System.out.println("--------------------");
            Business business = store.getBusiness();
            System.out.printf("Owned by: %s%n", business.getName());
            System.out.printf("Business Address: %s%n", business.getAddress());
            System.out.printf("Business EIN: %s%n", business.getEIN());
        }
        System.out.println("--------------------------------------------------");
    }
}