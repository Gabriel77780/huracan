package com.sc504.huracan.service;

import com.sc504.huracan.dto.SaleDetailDTO;
import com.sc504.huracan.dto.SaleRequestDTO;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public SaleService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private Long createSale(Long customerId, Integer systemUserId, BigDecimal total) {
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

  private void createSaleDetail(Long saleId, Long productId, Integer quantity,
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

  public void executeSale(SaleRequestDTO saleRequestDTO) {

    Long saleId = createSale(saleRequestDTO.customerId(), 1,
        calculateSaleTotal(saleRequestDTO.saleDetailDTOS()));

    for (SaleDetailDTO saleDetailDTO : saleRequestDTO.saleDetailDTOS()) {
      createSaleDetail(
          saleId,
          saleDetailDTO.productId(),
          saleDetailDTO.quantity(),
          saleDetailDTO.price(),
          saleDetailDTO.price()
              .multiply(BigDecimal.valueOf(saleDetailDTO.quantity()))
      );
    }
  }

  private BigDecimal calculateSaleTotal(List<SaleDetailDTO> saleDetailDTOS) {
    return saleDetailDTOS.stream()
        .map(saleDetailDTO -> saleDetailDTO.price()
            .multiply(BigDecimal.valueOf(saleDetailDTO.quantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
