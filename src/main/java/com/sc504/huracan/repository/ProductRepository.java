package com.sc504.huracan.repository;

import com.sc504.huracan.model.Product;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

  private final JdbcTemplate jdbcTemplate;

  public ProductRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createProduct(Product product) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_product_sp");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_description", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_price", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_stock", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_category", Types.VARCHAR));

    jdbcCall.execute(
        Map.of(
            "p_name", product.getName(),
            "p_description", product.getDescription(),
            "p_price", product.getPrice(),
            "p_stock", product.getStock(),
            "p_category", product.getCategory()
        )
    );
  }

  public Product getProductById(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_product_by_id_sp")
        .declareParameters(
            new SqlParameter("p_id", Types.NUMERIC),
            new SqlOutParameter("p_name", Types.VARCHAR),
            new SqlOutParameter("p_description", Types.VARCHAR),
            new SqlOutParameter("p_price", Types.DECIMAL),
            new SqlOutParameter("p_stock", Types.INTEGER),
            new SqlOutParameter("p_category", Types.VARCHAR),
            new SqlOutParameter("p_created_at", Types.TIMESTAMP)
        )
        .returningResultSet("product", new BeanPropertyRowMapper<>(Product.class));

    Map<String, Object> result = jdbcCall.execute(
        Map.of("p_id", id)
    );

    return new Product(
        id,
        (String) result.get("p_name"),
        (String) result.get("p_description"),
        (BigDecimal) result.get("p_price"),
        (Integer) result.get("p_stock"),
        (String) result.get("p_category"),
        (java.sql.Timestamp) result.get("p_created_at")
    );
  }

  public void updateProduct(Product product) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("update_product_sp");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_id", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_description", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_price", Types.DECIMAL));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_stock", Types.INTEGER));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_category", Types.VARCHAR));

    jdbcCall.execute(
        Map.of(
            "p_id", product.getId(),
            "p_name", product.getName(),
            "p_description", product.getDescription(),
            "p_price", product.getPrice(),
            "p_stock", product.getStock(),
            "p_category", product.getCategory()
        )
    );
  }

  public boolean deleteProduct(Long id) {

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("delete_product_sp");

    jdbcCall.execute(Map.of("p_id", id));

    return true;

  }

  public List<Product> getAllProducts() {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_all_product_sp")
        .returningResultSet("p_product_cursor",
            (RowMapper<Product>) (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("stock"),
                rs.getString("category"),
                null
            ));

    Map<String, Object> result = jdbcCall.execute();

    return result.get("p_product_cursor") != null
        ? (List<Product>) result.get("p_product_cursor")
        : List.of();
  }

  public boolean isProductAvailable(Long productId) {

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withFunctionName("is_product_available_fn");

    BigDecimal result = jdbcCall
        .executeFunction(BigDecimal.class, Map.of("p_product_id", productId));

    return result != null && result.compareTo(BigDecimal.ONE) == 0;
  }

}
