package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String street;

    @NonNull
    private String street2;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String zipCode;

    @OneToOne(mappedBy = "address")
    private Business business;

    @OneToOne(mappedBy = "address")
    private Store store;

    public Address(String street, String city, String state, String zipCode) {
        this.street = street;
        this.street2 = "";
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address(String street, String street2, String city, String state, String zipCode) {
        this.street = street;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public void setBusiness(Business business) {
        this.business = business;
        business.setAddress(this);
    }

    public void setStore(Store store) {
        this.store = store;
        store.setAddress(this);
    }

    @Override
    public int hashCode() {
        return this.street.hashCode() + this.street2.hashCode() + this.city.hashCode() + this.state.hashCode() + this.zipCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address address = (Address) obj;
        return this.street.equals(address.street) && this.street2.equals(address.street2) && this.city.equals(address.city) &&
               this.state.equals(address.state) && this.zipCode.equals(address.zipCode);
    }

    public String getFullAddress() {
        return this.street + " " + this.street2 + ", " + this.city + ", " + this.state + " " + this.zipCode;
    }
}