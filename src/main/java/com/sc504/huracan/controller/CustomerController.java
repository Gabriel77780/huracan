package com.sc504.huracan.controller;

import com.sc504.huracan.api.ApiResponseDTO;
import com.sc504.huracan.model.Customer;
import com.sc504.huracan.model.Product;
import com.sc504.huracan.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseDTO> createCustomer(@RequestBody Customer customer) {
    customerService.createCustomer(customer);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Cliente creado correctamente"));
  }

  @GetMapping("/{id}")
  public Customer getCustomer(@PathVariable Long id) {
    return customerService.getCustomerById(id);
  }

  @PutMapping
  public ResponseEntity<ApiResponseDTO> updateCustomer(@RequestBody Customer customer) {
    customerService.updateCustomer(customer);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Cliente actualizado correctamente"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseDTO> deleteCustomer(@PathVariable Long id) {

    boolean success = customerService.deleteCustomer(id);

    if (success) {
      return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
          "Cliente eliminado correctamente"));
    }

    return ResponseEntity.ok(new ApiResponseDTO(false, HttpStatus.NOT_FOUND.value(),
        "Cliente no encontrado"));
  }

  @GetMapping("/all")
  public List<Customer> getAllCustomers() {
    return customerService.getAllCustomers();
  }

}
