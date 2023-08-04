package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.Customer;
import com.daniel.geramed.repository.CustomerRepository;
import com.daniel.geramed.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer update(Customer customer) {
        findById(customer.getId());
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found"));
    }

    @Override
    public void delete(String id) {
        Customer customer= findById(id);
        customerRepository.delete(customer);
    }
}
