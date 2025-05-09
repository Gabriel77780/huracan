package com.sc504.huracan.repository;

import com.sc504.huracan.dto.PurchaseSummaryDTO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseRepository {

  private final JdbcTemplate jdbcTemplate;

  public PurchaseRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Long createPurchase(Long supplierId, Integer systemUserId, BigDecimal total) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_purchase_sp")
        .declareParameters(
            new SqlParameter("p_supplier_id", Types.NUMERIC),
            new SqlParameter("p_system_user_id", Types.NUMERIC),
            new SqlParameter("p_total", Types.NUMERIC),
            new SqlOutParameter("p_purchase_id", Types.NUMERIC)
        );

    Map<String, Object> result = jdbcCall.execute(
        Map.of(
            "p_supplier_id", supplierId,
            "p_system_user_id", systemUserId,
            "p_total", total
        )
    );

    return ((Number) result.get("p_purchase_id")).longValue();
  }

  public void createPurchaseDetail(Long purchaseId, Long productId, Integer quantity,
      BigDecimal price, BigDecimal subtotal) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("create_purchase_detail_sp")
        .declareParameters(
            new SqlParameter("p_purchase_id", Types.NUMERIC),
            new SqlParameter("p_product_id", Types.NUMERIC),
            new SqlParameter("p_quantity", Types.NUMERIC),
            new SqlParameter("p_price", Types.NUMERIC),
            new SqlParameter("p_subtotal", Types.NUMERIC)
        );

    jdbcCall.execute(
        Map.of(
            "p_purchase_id", purchaseId,
            "p_product_id", productId,
            "p_quantity", quantity,
            "p_price", price,
            "p_subtotal", subtotal
        )
    );
  }

  public PurchaseSummaryDTO getPurchaseSummaryById(Long purchaseId) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("get_purchase_summary_by_id_sp")
        .declareParameters(
            new SqlParameter("p_purchase_id", Types.NUMERIC),
            new SqlOutParameter("p_supplier_name", Types.VARCHAR),
            new SqlOutParameter("p_total_amount", Types.NUMERIC),
            new SqlOutParameter("p_sale_date", Types.VARCHAR)
        );

    Map<String, Object> result = jdbcCall.execute(Map.of("p_purchase_id", purchaseId));

    return new PurchaseSummaryDTO(
        purchaseId,
        (String) result.get("p_supplier_name"),
        (BigDecimal) result.get("p_total_amount"),
        (String) result.get("p_sale_date")
    );
  }

}
