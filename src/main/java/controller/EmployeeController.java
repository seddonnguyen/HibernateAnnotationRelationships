package controller;

import model.Business;
import model.Employee;
import model.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeController {
    public static Employee get(Session session, Long id) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Retrieving Employee ID: %d%n", id);
        System.out.println("--------------------------------------------------");

        Employee employee = session.get(Employee.class, id);

        if (employee == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Employee Not Found!");
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("Employee Retrieved Successfully!");
            System.out.printf("Employee ID: %d%n", employee.getId());
            System.out.printf("Employee Name: %s%n", employee.getFullName());
            System.out.printf("Employee SSN: %s%n", employee.getSSN());
            System.out.println("--------------------------------------------------");
        }
        return employee;
    }

    public static Employee persist(Session session, String firstName, String lastName, String ssn) {
        return persist(session, new Employee(firstName, lastName, ssn));
    }

    public static Employee persist(Session session, Employee employee) {
        Transaction transaction = null;

        if (employee == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Employee is null!");
            System.out.println("--------------------------------------------------");
            return null;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.println("Adding Employee");
            System.out.println("--------------------------------------------------");

            session.persist(employee);
            transaction.commit();

            System.out.println("--------------------------------------------------");
            System.out.println("Employee Added Successfully!");
            System.out.printf("Employee ID: %d%n", employee.getId());
            System.out.printf("Employee Name: %s%n", employee.getFullName());
            System.out.printf("Employee SSN: %s%n", employee.getSSN());
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Failed to Add Employee!");
            System.out.println("--------------------------------------------------");
        }
        return employee;
    }

    public static void delete(Session session, Long id) {
        Employee employee = session.get(Employee.class, id);

        if (employee == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Employee Not Found!");
            System.out.println("--------------------------------------------------");
            return;
        }
        delete(session, employee);
    }

    public static void delete(Session session, Employee employee) {
        Transaction transaction = null;

        if (employee == null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Employee is null");
            System.out.println("--------------------------------------------------");
            return;
        }

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Deleting Employee ID: %d%n", employee.getId());
            System.out.println("--------------------------------------------------");

            session.remove(employee);
            transaction.commit();
            session.evict(employee);

            System.out.println("--------------------------------------------------");
            System.out.println("Deleted Employee Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Delete Employee Failed!");
            System.out.println("--------------------------------------------------");
        }
    }

    public static Employee update(Session session, Employee employee) {
        Transaction transaction = null;
        Employee updatedEmployee = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("--------------------------------------------------");
            System.out.printf("Updating Employee ID: %d%n", employee.getId());
            System.out.println("--------------------------------------------------");

            updatedEmployee = session.merge(employee);
            transaction.commit();
            session.evict(employee);

            System.out.println("--------------------------------------------------");
            System.out.println("Employee Updated Successfully!");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();

            System.out.println("--------------------------------------------------");
            System.out.println("Update Employee Failed!");
            System.out.println("--------------------------------------------------");
        }
        return updatedEmployee;
    }

    public static void showInfo(Employee employee) {
        System.out.println("--------------------------------------------------");
        System.out.printf("Employee Name: %s%n", employee.getFullName());
        System.out.printf("Employee SSN: %s%n", employee.getSSN());

        System.out.println("--------------------");
        Store store = employee.getStore();
        System.out.printf("Store Name: %s%n", store.getName());
        System.out.printf("Store Address: %s%n", store.getAddress());

        System.out.println("--------------------");
        Business business = store.getBusiness();
        System.out.printf("Employer: %s%n", business.getName());
        System.out.printf("Employer Address: %s%n", business.getAddress());
        System.out.printf("Employer EIN: %s%n", business.getEIN());
        System.out.println("--------------------------------------------------");
    }
}