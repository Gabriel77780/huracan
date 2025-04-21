package com.sc504.huracan.controller;

import com.sc504.huracan.api.ApiResponseDTO;
import com.sc504.huracan.model.Supplier;
import com.sc504.huracan.service.SupplierService;
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
@RequestMapping("/supplier")
public class SupplierController {

  private final SupplierService supplierService;

  @Autowired
  public SupplierController(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseDTO> createSupplier(@RequestBody Supplier supplier) {
    supplierService.createSupplier(supplier);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Proveedor creado correctamente"));
  }

  @GetMapping("/{id}")
  public Supplier getProduct(@PathVariable Long id) {
    return supplierService.getSupplierById(id);
  }

  @PutMapping
  public ResponseEntity<ApiResponseDTO> updateProduct(@RequestBody Supplier supplier) {
    supplierService.updateSupplier(supplier);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Proveedor actualizado correctamente"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseDTO> deleteProduct(@PathVariable Long id) {

    boolean success = supplierService.deleteSupplier(id);

    if (success) {
      return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
          "Proveedor eliminado correctamente"));
    }

    return ResponseEntity.ok(new ApiResponseDTO(false, HttpStatus.NOT_FOUND.value(),
        "Proveedor no encontrado"));
  }

  @GetMapping("/all")
  public List<Supplier> getAllProducts() {
    return supplierService.getAllSuppliers();
  }

}
