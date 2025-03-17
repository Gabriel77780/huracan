package com.sc504.huracan.controller;

import com.sc504.huracan.model.Product;
import com.sc504.huracan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping
  public String createProduct(@RequestBody Product product) {
    productService.createProduct(product);
    return "Product created successfully";
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable Long id) {
    return productService.getProductById(id);
  }

  @PutMapping("/{id}")
  public String updateProduct(@PathVariable Long id, @RequestBody Product product) {
    productService.updateProduct(id, product);
    return "Product updated successfully";
  }

  @DeleteMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "Product deleted successfully";
  }

}