package com.company.service;

import com.company.database.Database;
import com.company.model.Customer;
import org.telegram.telegrambots.meta.api.objects.Contact;

public class CustomerService {
    public static Customer getCustomerByUserId(String userId) {
        for (Customer customer : Database.CUSTOMER_LIST) {
            if(customer.getUserId().equals(userId)){
                return customer;
            }
        }
        return null;
    }

    public static void addCustomer(Contact contact, String userId) {
        Customer customer = new Customer(contact.getFirstName(), contact.getLastName(), userId, contact.getPhoneNumber());
        Database.CUSTOMER_LIST.add(customer);
    }
}
