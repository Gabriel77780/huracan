package com.sc504.huracan.service;

import com.sc504.huracan.dto.PurchaseDetailDTO;
import com.sc504.huracan.dto.PurchaseRequestDTO;
import com.sc504.huracan.dto.SaleRequestDTO;
import com.sc504.huracan.repository.PurchaseRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

  private final PurchaseRepository purchaseRepository;

  public PurchaseService(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  public void executePurchase(PurchaseRequestDTO purchaseRequestDTO) {

    Long purchaseId = purchaseRepository.createPurchase(purchaseRequestDTO.supplierId(),
        1,
        calculatePurchaseTotal(purchaseRequestDTO.purchaseDetailDTOS()));

    for (var purchaseDetailDTO : purchaseRequestDTO.purchaseDetailDTOS()) {
      purchaseRepository.createPurchaseDetail(
          purchaseId,
          purchaseDetailDTO.productId(),
          purchaseDetailDTO.quantity(),
          purchaseDetailDTO.price(),
          purchaseDetailDTO.price()
              .multiply(BigDecimal.valueOf(purchaseDetailDTO.quantity()))
      );
    }
  }

  private BigDecimal calculatePurchaseTotal(List<PurchaseDetailDTO> purchaseDetailDTOS) {
    return purchaseDetailDTOS.stream()
        .map(purchaseDetailDTO -> purchaseDetailDTO.price()
            .multiply(BigDecimal.valueOf(purchaseDetailDTO.quantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
