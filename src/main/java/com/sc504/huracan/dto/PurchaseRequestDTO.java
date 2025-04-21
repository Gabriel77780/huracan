package com.sc504.huracan.dto;

import java.util.List;

public record PurchaseRequestDTO(Long supplierId, List<PurchaseDetailDTO> purchaseDetailDTOS)  {

}
