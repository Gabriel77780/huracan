package com.sc504.huracan.service;

import com.sc504.huracan.model.Product;
import java.sql.Types;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    
    
  @Autowired
  private JdbcTemplate jdbcTemplate;


  public void createProduct(Product product) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_product");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_description", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_price", Types.DECIMAL));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_stock", Types.INTEGER));
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
        .withProcedureName("get_product_by_id")
        .declareParameters(
            new SqlParameter("p_id", Types.NUMERIC),
            new SqlParameter("p_name", Types.VARCHAR),
            new SqlParameter("p_description", Types.VARCHAR),
            new SqlParameter("p_price", Types.DECIMAL),
            new SqlParameter("p_stock", Types.INTEGER),
            new SqlParameter("p_category", Types.VARCHAR),
            new SqlParameter("p_created_at", Types.TIMESTAMP)
        );

    Map<String, Object> result = jdbcCall.execute(
        Map.of("p_id", id)
    );

    return new Product(
        id,
        (String) result.get("p_name"),
        (String) result.get("p_description"),
        (Double) result.get("p_price"),
        (Integer) result.get("p_stock"),
        (String) result.get("p_category"),
        (java.sql.Timestamp) result.get("p_created_at")
    );
  }

  public void updateProduct(Long id, Product product) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("update_product");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_id", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_description", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_price", Types.DECIMAL));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_stock", Types.INTEGER));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_category", Types.VARCHAR));

    jdbcCall.execute(
        Map.of(
            "p_id", id,
            "p_name", product.getName(),
            "p_description", product.getDescription(),
            "p_price", product.getPrice(),
            "p_stock", product.getStock(),
            "p_category", product.getCategory()
        )
    );
  }

  public void deleteProduct(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("delete_product");

    jdbcCall.execute(Map.of("p_id", id));
  }
}