package com.sc504.huracan.dto;

import java.math.BigDecimal;

public record PurchaseSummaryDTO(Long purchaseId,
                                 String supplierName,
                                 BigDecimal totalAmount,
                                 String paidDate) {

}
