package com.sc504.huracan.service;

import com.sc504.huracan.model.Supplier;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

  private final JdbcTemplate jdbcTemplate;

  public SupplierService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createSupplier(Supplier supplier) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_supplier");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_phone", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_name", supplier.getName(),
        "p_phone", supplier.getPhone(),
        "p_email", supplier.getEmail()
    ));
  }

  public Supplier getSupplierById(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_supplier_by_id")
        .declareParameters(
            new SqlParameter("p_id", Types.NUMERIC),
            new SqlParameter("p_name", Types.VARCHAR),
            new SqlParameter("p_phone", Types.VARCHAR),
            new SqlParameter("p_email", Types.VARCHAR),
            new SqlParameter("p_created_at", Types.TIMESTAMP)
        );

    Map<String, Object> result = jdbcCall.execute(Map.of("p_id", id));

    return new Supplier(
        id,
        (String) result.get("p_name"),
        (String) result.get("p_phone"),
        (String) result.get("p_email"),
        (Timestamp) result.get("p_created_at")
    );
  }

  public void updateSupplier(Long id, Supplier supplier) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("update_supplier");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_id", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_phone", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_id", id,
        "p_name", supplier.getName(),
        "p_phone", supplier.getPhone(),
        "p_email", supplier.getEmail()
    ));
  }

  public void deleteSupplier(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("delete_supplier");

    jdbcCall.execute(Map.of("p_id", id));
  }
}
