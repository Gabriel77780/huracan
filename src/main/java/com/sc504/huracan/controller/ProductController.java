package com.sc504.huracan.controller;

import com.sc504.huracan.api.ApiResponseDTO;
import com.sc504.huracan.model.Product;
import com.sc504.huracan.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseDTO> createProduct(@RequestBody Product product) {
    productService.createProduct(product);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Producto creado correctamente"));
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable Long id) {
    return productService.getProductById(id);
  }

  @PutMapping
  public ResponseEntity<ApiResponseDTO> updateProduct(@RequestBody Product product) {
    productService.updateProduct(product);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Producto actualizado correctamente"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseDTO> deleteProduct(@PathVariable Long id) {

    boolean success = productService.deleteProduct(id);

    if (success) {
      return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
          "Producto eliminado correctamente"));
    }

    return ResponseEntity.ok(new ApiResponseDTO(false, HttpStatus.NOT_FOUND.value(),
        "Producto no encontrado"));
  }

  @GetMapping("/all")
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

}