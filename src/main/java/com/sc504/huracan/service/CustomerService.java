package com.sc504.huracan.service;

import com.sc504.huracan.model.Customer;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
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
        .withProcedureName("create_customer_sp");

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
        .withProcedureName("get_customer_by_id_sp")
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

  public void updateCustomer(Customer customer) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("update_customer_sp");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_id", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_phone", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_id", customer.getId(),
        "p_name", customer.getName(),
        "p_email", customer.getEmail(),
        "p_phone", customer.getPhone()
    ));
  }

  public boolean deleteCustomer(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("delete_customer_sp");

    jdbcCall.execute(Map.of("p_id", id));

    return true;
  }

  public List<Customer> getAllCustomers() {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_all_customer_sp")
        .declareParameters(
            new SqlParameter("p_id", Types.NUMERIC),
            new SqlParameter("p_name", Types.VARCHAR),
            new SqlParameter("p_email", Types.VARCHAR),
            new SqlParameter("p_phone", Types.VARCHAR),
            new SqlParameter("p_created_at", Types.TIMESTAMP)
        );

    Map<String, Object> result = jdbcCall.execute(Map.of());

    return (List<Customer>) result.get("customers");
  }

}
