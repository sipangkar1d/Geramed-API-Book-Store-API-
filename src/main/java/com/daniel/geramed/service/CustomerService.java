package com.daniel.geramed.service;

import com.daniel.geramed.entity.Customer;
import com.daniel.geramed.model.request.CustomerRequest;

public interface CustomerService {
    Customer create(Customer customer);
    Customer update(CustomerRequest request);
    Customer findById(String id);
}
