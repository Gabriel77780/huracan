package com.sc504.huracan.dto;

import java.util.List;

public record SaleRequestDTO(Long customerId,
                             List<SaleDetailDTO> saleDetailDTOS) {}
