package com.sc504.huracan.repository;

import com.sc504.huracan.dto.SaleSummaryDTO;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class SaleRepository {

  private final JdbcTemplate jdbcTemplate;

  public SaleRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Long createSale(Long customerId, Integer systemUserId, BigDecimal total) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_sale_sp")
        .declareParameters(
            new SqlParameter("p_customer_id", Types.NUMERIC),
            new SqlParameter("p_system_user_id", Types.NUMERIC),
            new SqlParameter("p_total", Types.NUMERIC),
            new SqlOutParameter("p_sale_id", Types.NUMERIC)
        );

    Map<String, Object> result = jdbcCall.execute(
        Map.of(
            "p_customer_id", customerId,
            "p_system_user_id", systemUserId,
            "p_total", total
        )
    );

    return ((Number) result.get("p_sale_id")).longValue();
  }

  public void createSaleDetail(Long saleId, Long productId, Integer quantity,
      BigDecimal price, BigDecimal subtotal) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_sale_detail_sp")
        .declareParameters(
            new SqlParameter("p_sale_id", Types.NUMERIC),
            new SqlParameter("p_product_id", Types.NUMERIC),
            new SqlParameter("p_quantity", Types.NUMERIC),
            new SqlParameter("p_price", Types.NUMERIC),
            new SqlParameter("p_subtotal", Types.NUMERIC)
        );

    jdbcCall.execute(
        Map.of(
            "p_sale_id", saleId,
            "p_product_id", productId,
            "p_quantity", quantity,
            "p_price", price,
            "p_subtotal", subtotal
        )
    );
  }

  public SaleSummaryDTO getSaleSummaryById(Long saleId) {

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_sale_summary_by_id_sp")
        .declareParameters(
            new SqlParameter("p_sale_id", Types.NUMERIC),
            new SqlOutParameter("p_customer_name", Types.VARCHAR),
            new SqlOutParameter("p_total_amount", Types.NUMERIC),
            new SqlOutParameter("p_sale_date", Types.VARCHAR)
        );

    Map<String, Object> result = jdbcCall.execute(
        Map.of("p_sale_id", saleId)
    );

    String customerName = (String) result.get("p_customer_name");
    BigDecimal totalAmount = (BigDecimal) result.get("p_total_amount");
    String saleDate = (String) result.get("p_sale_date");

    return new SaleSummaryDTO(saleId, customerName, totalAmount, saleDate);
  }

}
