package com.sc504.huracan.service;

import com.sc504.huracan.model.Product;
import com.sc504.huracan.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  public void createProduct(Product product) {
    productRepository.createProduct(product);
  }

  public Product getProductById(Long id) {
    return productRepository.getProductById(id);
  }

  public void updateProduct(Product product) {
    productRepository.updateProduct(product);
  }

  public boolean deleteProduct(Long id) {

    return productRepository.deleteProduct(id);

  }

  public List<Product> getAllProducts() {
    return productRepository.getAllProducts();
  }

  public boolean isProductAvailable(Long productId) {
   return productRepository.isProductAvailable(productId);
  }

}

