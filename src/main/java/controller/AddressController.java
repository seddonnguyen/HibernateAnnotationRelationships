package controller;

import model.Address;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AddressController {
    public static void delete(Session session, Long id) {
        Address address = session.get(Address.class, id);

        if (address == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Address not found!");
            System.out.println("--------------------------------------------------");
            return;
        }
        delete(session, address);
    }

    public static void delete(Session session, Address address) {
        Transaction transaction = null;

        if (address == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Address is null");
            System.out.println("--------------------------------------------------");
            return;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Deleting Address ID: %d%n", address.getId());
            System.out.println("--------------------------------------------------");

            session.remove(address);
            transaction.commit();
            session.evict(address);

            System.out.println("--------------------------------------------------");
            System.out.println("Address Deleted Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Delete Address Failed!");
            System.out.println("--------------------------------------------------");
        }
    }

    public static Address update(Session session, Address address) {
        Transaction transaction = null;
        Address updatedAddress = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Updating Address ID: %d%n", address.getId());
            System.out.println("--------------------------------------------------");

            updatedAddress = session.merge(address);
            transaction.commit();
            session.evict(address);

            System.out.println("--------------------------------------------------");
            System.out.println("Address Updated Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Update Address Failed!");
            System.out.println("--------------------------------------------------");
        }
        return updatedAddress;
    }

    public static Address persist(Session session, String street, String street2, String city, String state, String zip) {
        return persist(session, new Address(street, street2, city, state, zip));
    }

    public static Address persist(Session session, Address address) {
        Transaction transaction = null;

        if (address == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Address is null");
            System.out.println("--------------------------------------------------");
            return null;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.println("Adding Address");
            System.out.println("--------------------------------------------------");

            session.persist(address);
            transaction.commit();

            System.out.println("--------------------------------------------------");
            System.out.println("Address Added Successfully!");
            System.out.printf("Address ID: %d%n", address.getId());
            System.out.printf("Full Address: %s%n", address.getFullAddress());
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Add Address Failed!");
            System.out.println("--------------------------------------------------");
        }
        return address;
    }

    public static void showInfo(Address address) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Address ID: %d%n", address.getId());
        System.out.printf("Full Address: %s%n", address.getFullAddress());
        System.out.printf("Street: %s%n", address.getStreet());
        System.out.printf("Street 2: %s%n", address.getStreet2());
        System.out.printf("City: %s%n", address.getCity());
        System.out.printf("State: %s%n", address.getState());
        System.out.printf("Zip Code: %s%n", address.getZipCode());
        System.out.println("--------------------------------------------------");
    }
}