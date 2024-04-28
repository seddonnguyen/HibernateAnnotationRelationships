package Runner;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            populate(session);

            System.out.println("--------------------------------------------------");
            System.out.println("Get Book and Store by ID");
            System.out.println("--------------------------------------------------");
            getBookById(session, 1L);

            System.out.println("--------------------------------------------------");
            System.out.println("Get Store and Book by ID");
            System.out.println("--------------------------------------------------");
            getStoreById(session, 1L);

            System.out.println("--------------------------------------------------");
            System.out.println("Get Author and Book by ID");
            System.out.println("--------------------------------------------------");
            getAuthorById(session, 1L);

            System.out.println("--------------------------------------------------");
            System.out.println("Get Business and Store by ID");
            System.out.println("--------------------------------------------------");
            getBusinessById(session, 1L);

            System.out.println("--------------------------------------------------");
            System.out.println("Get Employee by ID");
            System.out.println("--------------------------------------------------");
            getEmployeeById(session, 1L);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populate(Session session) {
        Transaction transaction = null;
        try {
            System.out.println("--------------------------------------------------");
            System.out.println("Populating the database");
            System.out.println("--------------------------------------------------");

            transaction = session.beginTransaction();

            Address address1 = new Address("123 Main St", "Apt 1", "New York", "NY", "10001");
            Address address2 = new Address("456 Elm St", "Apt 2", "New York", "NY", "10002");
            Address address3 = new Address("789 Oak St", "Apt 3", "New York", "NY", "10003");
            Address address4 = new Address("101 Pine St", "Apt 4", "New York", "NY", "10004");
            Address address5 = new Address("112 Maple St", "Apt 5", "New York", "NY", "10005");


            Business business1 = new Business("Business 1", "135791113");
            Business business2 = new Business("Business 2", "2468101214");

            business1.setAddress(address1);
            business2.setAddress(address2);

            Store store1 = new Store("Store 1");
            Store store2 = new Store("Store 2");
            Store store3 = new Store("Store 3");

            store1.setAddress(address3);
            store2.setAddress(address4);
            store3.setAddress(address5);

            business1.addStore(store1);
            business1.addStore(store2);
            business2.addStore(store3);

            session.persist(business1);
            session.persist(business2);

            Employee employee1 = new Employee("John", "Doe", "123-45-6789");
            Employee employee2 = new Employee("Jane", "Doe", "987-65-4321");
            Employee employee3 = new Employee("Alice", "Smith", "123-78-9456");
            Employee employee4 = new Employee("Bob", "Smith", "987-65-4321");
            Employee employee5 = new Employee("Charlie", "Brown", "123-45-6789");
            Employee employee6 = new Employee("Daisy", "Duck", "123-78-9456");
            Employee employee7 = new Employee("Eve", "Smith", "987-65-4321");

            store1.addEmployee(employee1);
            store1.addEmployee(employee2);
            store1.addEmployee(employee3);

            store2.addEmployee(employee4);
            store2.addEmployee(employee5);

            store3.addEmployee(employee6);
            store3.addEmployee(employee7);
            store3.addEmployee(employee1);


            Author author1 = new Author("John", "Doe");
            Author author2 = new Author("Jane", "Doe");
            Author author3 = new Author("Alice", "Smith");

            Book book1 = new Book("Book 1", "123456789");
            Book book2 = new Book("Book 2", "987654321");
            Book book3 = new Book("Book 3", "123789456");

            author1.addBook(book1);
            author1.addBook(book2);
            author1.addBook(book3);

            author2.addBook(book1);
            author2.addBook(book2);
            author2.addBook(book3);

            author3.addBook(book1);
            author3.addBook(book2);
            author3.addBook(book3);

            store1.addBook(book1);
            store1.addBook(book2);
            store1.addBook(book3);

            store2.addBook(book1);
            store2.addBook(book2);
            store2.addBook(book3);

            store3.addBook(book1);
            store3.addBook(book2);
            store3.addBook(book3);

            session.persist(store1);
            session.persist(store2);
            session.persist(store3);

            transaction.commit();

            System.out.println("--------------------------------------------------");
            System.out.println("Database populated successfully");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void getBookById(Session session, Long id) {
        Book book = session.get(Book.class, id);
        String title = book.getTitle();
        Set<Store> stores = book.getStores();

        System.out.printf("Book Title: %s%n", title);
        System.out.printf("Book ISBN: %s%n", book.getIsbn());
        System.out.println("--------------------");
        System.out.printf("Number of Authors: %d%n", book.getAuthors().size());

        System.out.println("--------------------");
        for (Author author : book.getAuthors()) {
            System.out.printf("Author Name: %s%n", author.getFullName());
        }

        System.out.println("--------------------");
        System.out.printf("Sold in %d stores%n", stores.size());

        System.out.println("--------------------");
        for (Store store : stores) {
            System.out.printf("Store Name: %s%n", store.getName());
        }
    }

    public static void getStoreById(Session session, Long id) {
        Store store = session.get(Store.class, id);
        String name = store.getName();
        Set<Book> books = store.getBooks();

        System.out.printf("Store Name: %s%n", name);
        System.out.printf("Store Address: %s%n", store.getAddress());
        System.out.printf("Owned by Business: %s%n", store.getBusiness().getName());
        System.out.println("--------------------");

        System.out.printf("Employees in store: %d%n", store.getEmployees().size());
        for (Employee employee : store.getEmployees()) {
            System.out.printf("Employee Name: %s%n", employee.getFullName());
        }
        System.out.println("--------------------");


        System.out.printf("Books in store: %d%n", books.size());
        for (Book book : books) {
            System.out.println("--------------------");
            System.out.printf("Book Title: %s%n", book.getTitle());
            System.out.printf("Book ISBN: %s%n", book.getIsbn());

            System.out.println("--------------------");
            System.out.printf("Number of Authors: %d%n", book.getAuthors().size());

            System.out.println("--------------------");
            for (Author author : book.getAuthors()) {
                System.out.printf("Author Name: %s%n", author.getFullName());
            }
        }
    }

    public static void getAuthorById(Session session, Long id) {
        Author author = session.get(Author.class, id);
        String fullName = author.getFullName();
        Set<Book> books = author.getBooks();

        System.out.printf("Author Name: %s%n", fullName);
        System.out.printf("Number of Books: %d%n", books.size());

        for (Book book : books) {
            System.out.println("--------------------");
            System.out.printf("Book Title: %s%n", book.getTitle());
            System.out.printf("Book ISBN: %s%n", book.getIsbn());
        }
    }

    public static void getBusinessById(Session session, Long id) {
        Business business = session.get(Business.class, id);
        String name = business.getName();
        Set<Store> stores = business.getStores();

        System.out.printf("Business Name: %s%n", name);
        System.out.printf("Business EIN: %s%n", business.getEIN());
        System.out.printf("Business Address: %s%n", business.getAddress());

        System.out.println("--------------------");
        System.out.printf("Number of Stores: %d%n", stores.size());
        for (Store store : stores) {
            System.out.println("--------------------");
            System.out.printf("Store Name: %s%n", store.getName());
            System.out.printf("Store Address: %s%n", store.getAddress());
        }

        System.out.println("--------------------");
        System.out.printf("Number of Employees: %d%n", business.getEmployees().size());
        for (Employee employee : business.getEmployees()) {
            System.out.printf("Employee Name: %s%n", employee.getFullName());
        }
    }

    public static void getEmployeeById(Session session, Long id) {
        Employee employee = session.get(Employee.class, id);
        String fullName = employee.getFullName();
        Store store = employee.getStore();
        Business business = store.getBusiness();

        System.out.printf("Employee Name: %s%n", fullName);
        System.out.printf("Employee SSN: %s%n", employee.getSSN());
        System.out.printf("Work Location: %s%n", store.getName());
        System.out.printf("Store Address: %s%n", store.getAddress());
        System.out.printf("Employer: %s%n", business.getName());
        System.out.printf("Employer Address: %s%n", business.getAddress());
    }

}