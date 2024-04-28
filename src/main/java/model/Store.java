package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id")
    private Business business;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToMany(mappedBy = "stores", cascade = CascadeType.ALL)
    private Set<Book> books;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public Store() {
        this.books = new HashSet<>();
        this.employees = new HashSet<>();
    }

    public Store(String name) {
        this.name = name;
        this.books = new HashSet<>();
        this.employees = new HashSet<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getStores().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getStores().remove(this);
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setStore(this);
        business.getEmployees().add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setStore(null);
        business.getEmployees().remove(employee);
    }

    public String getAddress() {
        return this.address.getFullAddress();
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Store store = (Store) obj;
        return name.equals(store.name);
    }
}