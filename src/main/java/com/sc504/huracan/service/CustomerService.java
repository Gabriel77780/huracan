package com.sc504.huracan.service;

import com.sc504.huracan.exception.SystemException;
import com.sc504.huracan.model.Customer;
import com.sc504.huracan.model.Product;
import com.sc504.huracan.repository.CustomerRepository;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void createCustomer(Customer customer) {
    customerRepository.createCustomer(customer);
  }

  public Customer getCustomerById(Long id) {
    return customerRepository.getCustomerById(id);
  }

  public void updateCustomer(Customer customer) {
    customerRepository.updateCustomer(customer);
  }

  public boolean deleteCustomer(Long id) {

    if (customerRepository.isCustomerInUse(id)) {
      throw new SystemException("El cliente no se puede eliminar porque está en uso.");
    }

    return customerRepository.deleteCustomer(id);
  }

  public List<Customer> getAllCustomers() {
    return customerRepository.getAllCustomers();
  }



}
