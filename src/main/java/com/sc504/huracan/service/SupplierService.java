package com.sc504.huracan.service;

import com.sc504.huracan.model.Supplier;
import com.sc504.huracan.repository.SupplierRepository;
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
public class SupplierService {

  private final SupplierRepository supplierRepository;

  public SupplierService(SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  public void createSupplier(Supplier supplier) {
    supplierRepository.createSupplier(supplier);
  }

  public Supplier getSupplierById(Long id) {
    return supplierRepository.getSupplierById(id);
  }

  public void updateSupplier(Supplier supplier) {
    supplierRepository.updateSupplier(supplier);
  }

  public boolean deleteSupplier(Long id) {
    return supplierRepository.deleteSupplier(id);
  }

  public List<Supplier> getAllSuppliers() {
    return supplierRepository.getAllSuppliers();
  }
}
