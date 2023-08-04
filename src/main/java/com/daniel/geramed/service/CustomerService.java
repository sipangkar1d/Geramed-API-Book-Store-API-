package com.daniel.geramed.service;

import com.daniel.geramed.entity.Customer;

public interface CustomerService {
    Customer create(Customer customer);
    Customer update(Customer customer);
    Customer findById(String id);
    void delete(String id);
}
