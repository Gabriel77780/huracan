package com.sc504.huracan.service;

import com.sc504.huracan.model.Customer;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final JdbcTemplate jdbcTemplate;

  public CustomerService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createCustomer(Customer customer) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_customer");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_phone", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_name", customer.getName(),
        "p_email", customer.getEmail(),
        "p_phone", customer.getPhone()
    ));
  }

  public Customer getCustomerById(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_customer_by_id")
        .declareParameters(
            new SqlParameter("p_id", Types.NUMERIC),
            new SqlParameter("p_name", Types.VARCHAR),
            new SqlParameter("p_email", Types.VARCHAR),
            new SqlParameter("p_phone", Types.VARCHAR),
            new SqlParameter("p_created_at", Types.TIMESTAMP)
        );

    Map<String, Object> result = jdbcCall.execute(Map.of("p_id", id));

    return new Customer(
        id,
        (String) result.get("p_name"),
        (String) result.get("p_email"),
        (String) result.get("p_phone"),
        (Timestamp) result.get("p_created_at")
    );
  }

  public void updateCustomer(Long id, Customer customer) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("update_customer");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_id", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_phone", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_id", id,
        "p_name", customer.getName(),
        "p_email", customer.getEmail(),
        "p_phone", customer.getPhone()
    ));
  }

  public void deleteCustomer(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("delete_customer");

    jdbcCall.execute(Map.of("p_id", id));
  }

}
