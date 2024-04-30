package Runner;

import controller.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            populate(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populate(Session session) {
        System.out.println("--------------------------------------------------");
        System.out.println("Populating the database");
        System.out.println("--------------------------------------------------");

        // Create businesses
        Business business1 = new Business("Business 1", "135791113");
        Business business2 = new Business("Business 2", "2468101214");

        Address businessAddress1 = new Address("123 Main St", "Apt 1", "New York", "NY", "10001");
        Address businessAddress2 = new Address("456 Elm St", "Apt 2", "New York", "NY", "10002");

        business1.setAddress(businessAddress1);
        business2.setAddress(businessAddress2);

        BusinessController.persist(session, business1);
        BusinessController.persist(session, business2);

        // Create store addresses
        Address storeAddress1 = AddressController.persist(session, "789 Oak St", "Apt 3", "New York", "NY", "10003");
        Address storeAddress2 = AddressController.persist(session, "101 Pine St", "Apt 4", "New York", "NY", "10004");
        Address storeAddress3 = AddressController.persist(session, "112 Maple St", "Apt 5", "New York", "NY", "10005");

        // Create stores
        Store store1 = new Store("Store 1");
        Store store2 = new Store("Store 2");
        Store store3 = new Store("Store 3");

        store1.setAddress(storeAddress1);
        store2.setAddress(storeAddress2);
        store3.setAddress(storeAddress3);

        store1.setBusiness(business1);
        store2.setBusiness(business1);
        store3.setBusiness(business2);

        StoreController.persist(session, store1);
        StoreController.persist(session, store2);
        StoreController.persist(session, store3);

        // Create employees
        Employee employee1 = EmployeeController.persist(session, "John", "Doe", "123-45-6789");
        Employee employee2 = EmployeeController.persist(session, "Jane", "Doe", "987-65-4321");
        Employee employee3 = EmployeeController.persist(session, "Alice", "Smith", "123-78-9456");
        Employee employee4 = EmployeeController.persist(session, "Bob", "Smith", "987-65-4321");
        Employee employee5 = EmployeeController.persist(session, "Charlie", "Brown", "123-45-6789");
        Employee employee6 = EmployeeController.persist(session, "Daisy", "Duck", "123-78-9456");
        Employee employee7 = EmployeeController.persist(session, "Eve", "Smith", "987-65-4321");

        // Update employees with stores
        employee1.setStore(store1);
        employee2.setStore(store1);
        employee3.setStore(store1);

        employee4.setStore(store2);
        employee5.setStore(store2);

        employee6.setStore(store3);
        employee7.setStore(store3);
        employee1.setStore(store3);

        EmployeeController.update(session, employee1);
        EmployeeController.update(session, employee2);
        EmployeeController.update(session, employee3);
        EmployeeController.update(session, employee4);
        EmployeeController.update(session, employee5);
        EmployeeController.update(session, employee6);
        EmployeeController.update(session, employee7);

        // Create authors
        Author author1 = AuthorController.persist(session, "John", "Doe");
        Author author2 = AuthorController.persist(session, "Jane", "Doe");
        Author author3 = AuthorController.persist(session, "Alice", "Smith");

        // Create books
        Book book1 = new Book("Book 1", "123456789");
        Book book2 = new Book("Book 2", "987654321");
        Book book3 = new Book("Book 3", "123789456");

        book1.addAuthor(author1);
        book1.addAuthor(author2);
        book1.addAuthor(author3);

        book2.addAuthor(author1);
        book2.addAuthor(author2);
        book2.addAuthor(author3);

        book3.addAuthor(author1);
        book3.addAuthor(author2);
        book3.addAuthor(author3);

        book1.addStore(store1);
        book1.addStore(store2);
        book1.addStore(store3);

        book2.addStore(store1);
        book2.addStore(store2);
        book2.addStore(store3);

        book3.addStore(store1);
        book3.addStore(store2);
        book3.addStore(store3);

        BookController.persist(session, book1);
        BookController.persist(session, book2);
        BookController.persist(session, book3);

        System.out.println("--------------------------------------------------");
        System.out.println("Database populated successfully");
        System.out.println("--------------------------------------------------");
    }
}