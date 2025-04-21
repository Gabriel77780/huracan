package com.sc504.huracan.service;

import com.sc504.huracan.model.Customer;
import com.sc504.huracan.model.Supplier;
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

  private final JdbcTemplate jdbcTemplate;

  public SupplierService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createSupplier(Supplier supplier) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_supplier_sp");

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
        .withProcedureName("get_supplier_by_id_sp")
        .declareParameters(
            new SqlParameter("p_id", Types.NUMERIC),
            new SqlOutParameter("p_name", Types.VARCHAR),
            new SqlOutParameter("p_phone", Types.VARCHAR),
            new SqlOutParameter("p_email", Types.VARCHAR),
            new SqlOutParameter("p_created_at", Types.TIMESTAMP)
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

  public void updateSupplier(Supplier supplier) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("update_supplier_sp");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_id", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_phone", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_id", supplier.getId(),
        "p_name", supplier.getName(),
        "p_phone", supplier.getPhone(),
        "p_email", supplier.getEmail()
    ));
  }

  public boolean deleteSupplier(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("delete_supplier_sp");

    jdbcCall.execute(Map.of("p_id", id));

    return true;
  }

  public List<Supplier> getAllSuppliers() {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_all_supplier_sp")
        .returningResultSet("p_supplier_cursor",
            (RowMapper<Supplier>) (rs, rowNum) -> new Supplier(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("email"),
                null
            ));

    Map<String, Object> result = jdbcCall.execute(Map.of());

    return result.get("p_supplier_cursor")!= null ?
        (List<Supplier>) result.get("p_supplier_cursor") : List.of();
  }
}
