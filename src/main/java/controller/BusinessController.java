package controller;

import model.Business;
import model.Employee;
import model.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class BusinessController {
    public static void delete(Session session, Long id) {
        Business business = session.get(Business.class, id);

        if (business == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Business not found!");
            System.out.println("--------------------------------------------------");
            return;
        }
        delete(session, business);
    }

    public static void delete(Session session, Business business) {
        Transaction transaction = null;

        if (business == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Business is null");
            System.out.println("--------------------------------------------------");
            return;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Deleting Business ID: %d%n", business.getId());
            System.out.println("--------------------------------------------------");

            session.remove(business);
            transaction.commit();
            session.evict(business);

            System.out.println("--------------------------------------------------");
            System.out.println("Business Deleted Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Delete Business Failed!");
            System.out.println("--------------------------------------------------");
        }
    }

    public static Business update(Session session, Business business) {
        Transaction transaction = null;
        Business updatedBusiness = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Updating Business ID: %d%n", business.getId());
            System.out.println("--------------------------------------------------");

            updatedBusiness = session.merge(business);
            transaction.commit();
            session.evict(business);

            System.out.println("--------------------------------------------------");
            System.out.println("Business Updated Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Update Business Failed!");
            System.out.println("--------------------------------------------------");
        }
        return updatedBusiness;
    }

    public static Business persist(Session session, String name, String EIN) {
        return persist(session, new Business(name, EIN));
    }

    public static Business persist(Session session, Business business) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.println("Adding a new Business");
            System.out.println("--------------------------------------------------");

            session.persist(business);
            transaction.commit();

            System.out.println("--------------------------------------------------");
            System.out.println("Business Added Successfully!");
            System.out.printf("Business ID: %d%n", business.getId());
            System.out.printf("Business Name: %s%n", business.getName());
            System.out.printf("Business EIN: %s%n", business.getEIN());
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Failed to Add Business!");
            System.out.println("--------------------------------------------------");
        }
        return business;
    }

    public static Business get(Session session, Long id) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Retrieving Business ID: %d%n", id);
        System.out.println("--------------------------------------------------");

        Business business = session.get(Business.class, id);

        if (business == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Business not found!");
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Business Retrieved Successfully!");
            System.out.printf("Business ID: %d%n", business.getId());
            System.out.printf("Business Name: %s%n", business.getName());
            System.out.printf("Business EIN: %s%n", business.getEIN());
            System.out.println("--------------------------------------------------");
        }
        return business;
    }

    public static void showInfo(Business business) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Business Name: %s%n", business.getName());
        System.out.printf("Business EIN: %s%n", business.getEIN());
        System.out.printf("Business Address: %s%n", business.getAddress());

        System.out.println("--------------------");
        Set<Store> stores = business.getStores();
        System.out.printf("Number of Stores: %d%n", stores.size());

        for (Store store : stores) {
            System.out.println("--------------------");
            System.out.printf("Store Name: %s%n", store.getName());
            System.out.printf("Store Address: %s%n", store.getAddress());

            System.out.println("--------------------");
            Set<Employee> employees = store.getEmployees();
            System.out.printf("Number of Employees: %d%n", employees.size());

            for (Employee employee : employees) {
                System.out.println("--------------------");
                System.out.printf("Employee Name: %s%n", employee.getFullName());
                System.out.printf("Employee SSN: %s%n", employee.getSSN());
            }
        }

        System.out.println("--------------------");
        Set<Employee> employees = business.getEmployees();
        System.out.printf("Business number of Employees: %d%n", employees.size());

        for (Employee employee : employees) {
            System.out.println("--------------------");
            System.out.printf("Employee Name: %s%n", employee.getFullName());
            System.out.printf("Employee SSN: %s%n", employee.getSSN());
        }
        System.out.println("--------------------------------------------------");
    }
}