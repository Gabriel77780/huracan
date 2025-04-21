package com.sc504.huracan.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleSummaryDTO(Long saleId,
                             String customerName,
                             BigDecimal totalAmount,
                             String paidDate) {
}
