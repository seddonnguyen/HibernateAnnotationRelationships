package controller;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class StoreController {
    public static Store get(Session session, Long id) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Retrieving Store ID: %d%n", id);
        System.out.println("--------------------------------------------------");

        Store store = session.get(Store.class, id);

        if (store == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Store not found");
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Store Retrieved Successfully!");
            System.out.printf("Store ID: %d%n", store.getId());
            System.out.printf("Store Name: %s%n", store.getName());
            System.out.println("--------------------------------------------------");
        }
        return store;
    }

    public static void delete(Session session, Long id) {
        Store store = session.get(Store.class, id);

        if (store == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Store not found!");
            System.out.println("--------------------------------------------------");
            return;
        }
        delete(session, store);
    }

    public static void delete(Session session, Store store) {
        Transaction transaction = null;

        if (store == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Store is null");
            System.out.println("--------------------------------------------------");
            return;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Deleting Store ID: %d%n", store.getId());
            System.out.println("--------------------------------------------------");

            session.remove(store);
            transaction.commit();
            session.evict(store);

            System.out.println("--------------------------------------------------");
            System.out.println("Store Deleted Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Delete Store Failed!");
            System.out.println("--------------------------------------------------");
        }
    }

    public static Store update(Session session, Store store) {
        Transaction transaction = null;
        Store updatedStore = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Updating Store ID: %d%n", store.getId());
            System.out.println("--------------------------------------------------");

            updatedStore = session.merge(store);
            transaction.commit();
            session.evict(store);

            System.out.println("--------------------------------------------------");
            System.out.println("Store Updated Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Update Store Failed!");
            System.out.println("--------------------------------------------------");
        }
        return updatedStore;
    }

    public static Store persist(Session session, String name) {
        return persist(session, new Store(name));
    }

    public static Store persist(Session session, Store store) {
        Transaction transaction = null;

        if (store == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Store is null");
            System.out.println("--------------------------------------------------");
            return null;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.println("Adding a new Store");
            System.out.println("--------------------------------------------------");

            session.persist(store);
            transaction.commit();

            System.out.println("--------------------------------------------------");
            System.out.println("Store Added Successfully!");
            System.out.printf("Store ID: %d%n", store.getId());
            System.out.printf("Store Name: %s%n", store.getName());
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Failed to Add Store!");
            System.out.println("--------------------------------------------------");
        }
        return store;
    }

    public static void showInfo(Store store) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Store ID: %d%n", store.getId());
        System.out.printf("Store Name: %s%n", store.getName());
        System.out.printf("Store Address: %s%n", store.getAddress());

        System.out.println("--------------------");
        Business business = store.getBusiness();
        System.out.printf("Owned by: %s%n", business.getName());
        System.out.printf("Business Address: %s%n", business.getAddress());
        System.out.printf("Business EIN: %s%n", business.getEIN());

        System.out.println("--------------------");
        Set<Employee> employees = store.getEmployees();
        System.out.printf("Number of Employees: %d%n", employees.size());

        for (Employee employee : employees) {
            System.out.println("--------------------");
            System.out.printf("Employee Name: %s%n", employee.getFullName());
            System.out.printf("Employee SSN: %s%n", employee.getSSN());
        }

        System.out.println("--------------------");
        Set<Book> books = store.getBooks();
        System.out.printf("Number of Books: %d%n", books.size());

        for (Book book : books) {
            System.out.println("--------------------");
            System.out.printf("Book Title: %s%n", book.getTitle());
            System.out.printf("Book ISBN: %s%n", book.getIsbn());

            System.out.println("--------------------");
            Set<Author> authors = book.getAuthors();
            System.out.printf("Number of Authors: %d%n", authors.size());

            for (Author author : authors) {
                System.out.println("--------------------");
                System.out.printf("Author Name: %s%n", author.getFullName());
            }
        }
        System.out.println("--------------------------------------------------");
    }
}