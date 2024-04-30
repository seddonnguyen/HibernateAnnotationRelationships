package controller;

import model.Author;
import model.Book;
import model.Business;
import model.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class AuthorController {
    public static void delete(Session session, Long id) {
        Author author = session.get(Author.class, id);

        if (author == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Author not found!");
            System.out.println("--------------------------------------------------");
            return;
        }
        delete(session, author);
    }

    public static void delete(Session session, Author author) {
        Transaction transaction = null;

        if (author == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Author is null");
            System.out.println("--------------------------------------------------");
            return;
        }

        try {
            transaction = session.beginTransaction();

            System.out.println("--------------------------------------------------");
            System.out.printf("Deleting Author ID: %d%n", author.getId());
            System.out.println("--------------------------------------------------");

            session.remove(author);
            transaction.commit();
            session.evict(author);

            System.out.println("--------------------------------------------------");
            System.out.println("Author Deleted Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Delete Author Failed!");
            System.out.println("--------------------------------------------------");
        }
    }

    public static Author get(Session session, Long id) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Retrieving Author ID: %d%n", id);
        System.out.println("--------------------------------------------------");

        Author author = session.get(Author.class, id);

        if (author == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Author not found!");
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Author Retrieved Successfully!");
            System.out.printf("Author ID: %d%n", author.getId());
            System.out.printf("Author Name: %s%n", author.getFullName());
            System.out.println("--------------------------------------------------");
        }
        return author;
    }

    public static Author persist(Session session, String firstName, String lastName) {
        return persist(session, new Author(firstName, lastName));
    }

    public static Author persist(Session session, Author author) {
        Transaction transaction = null;

        if (author == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Author is null");
            System.out.println("--------------------------------------------------");
            return null;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.println("Adding a new Author");
            System.out.println("--------------------------------------------------");

            session.persist(author);
            transaction.commit();

            System.out.println("--------------------------------------------------");
            System.out.println("Author Added Successfully!");
            System.out.printf("Author ID: %d%n", author.getId());
            System.out.printf("Author Name: %s%n", author.getFullName());
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Failed to Add Author!");
            System.out.println("--------------------------------------------------");
        }
        return author;
    }

    public static Author update(Session session, Author author) {
        Transaction transaction = null;
        Author updatedAuthor = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Updating Author ID: %d%n", author.getId());
            System.out.println("--------------------------------------------------");

            updatedAuthor = session.merge(author);
            transaction.commit();
            session.evict(author);

            System.out.println("--------------------------------------------------");
            System.out.println("Author Updated Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Update Author Failed!");
            System.out.println("--------------------------------------------------");
        }
        return updatedAuthor;
    }

    public static void showInfo(Author author) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Author ID: %d%n", author.getId());
        System.out.printf("Author Name: %s%n", author.getFullName());

        System.out.println("--------------------");
        Set<Book> books = author.getBooks();
        System.out.printf("Wrote %d books%n", books.size());

        for (Book book : books) {
            System.out.println("--------------------");
            System.out.printf("Book Title: %s%n", book.getTitle());
            System.out.printf("Book ISBN: %s%n", book.getIsbn());

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
        }
        System.out.println("--------------------------------------------------");
    }
}