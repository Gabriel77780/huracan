package com.sc504.huracan.repository;

import com.sc504.huracan.model.Customer;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

  private final JdbcTemplate jdbcTemplate;

  public CustomerRepository(JdbcTemplate jdbcTemplate) {
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
            new SqlOutParameter("p_name", Types.VARCHAR),
            new SqlOutParameter("p_email", Types.VARCHAR),
            new SqlOutParameter("p_phone", Types.VARCHAR),
            new SqlOutParameter("p_created_at", Types.TIMESTAMP)
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
        .returningResultSet("p_customer_cursor",
            (RowMapper<Customer>) (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                null
            ));

    Map<String, Object> result = jdbcCall.execute(Map.of());

    return result.get("p_customer_cursor")!= null ?
        (List<Customer>) result.get("p_customer_cursor") : List.of();
  }

  public boolean isCustomerInUse(Long customerId) {

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withFunctionName("is_customer_used_in_sale_fn");

    BigDecimal result = jdbcCall
        .executeFunction(BigDecimal.class, Map.of("p_customer_id", customerId));

    return result != null && result.compareTo(BigDecimal.ONE) == 0;
  }

}
