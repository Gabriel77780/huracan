package com.sc504.huracan.dto;

import java.math.BigDecimal;

public record SaleDetailDTO(Long productId,
                            int quantity, BigDecimal price) { }