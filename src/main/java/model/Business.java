package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String EIN;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Store> stores;
    
    @Transient
    private Set<Employee> employees;

    public Business() {
        this.stores = new HashSet<>();
        this.employees = new HashSet<>();
    }

    public Business(String name, String EIN) {
        this.name = name;
        this.EIN = EIN;
        this.stores = new HashSet<>();
        this.employees = new HashSet<>();
    }

    public void removeAddress() {
        if (this.address != null) {
            this.address.setBusiness(null);
            this.address = null;
        }
    }

    public void addStore(Store store) {
        this.stores.add(store);
        store.setBusiness(this);
    }

    public void removeStore(Store store) {
        this.stores.remove(store);
        store.setBusiness(null);
    }

    public String getAddress() {
        return this.address.getFullAddress();
    }

    @Override
    public int hashCode() {
        return this.EIN.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Business business = (Business) obj;
        return this.EIN.equals(business.EIN);
    }
}