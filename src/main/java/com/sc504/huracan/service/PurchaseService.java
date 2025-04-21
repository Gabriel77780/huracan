package com.sc504.huracan.service;

import com.sc504.huracan.dto.PurchaseDetailDTO;
import com.sc504.huracan.dto.PurchaseRequestDTO;
import com.sc504.huracan.dto.PurchaseSummaryDTO;
import com.sc504.huracan.dto.SaleRequestDTO;
import com.sc504.huracan.dto.SaleSummaryDTO;
import com.sc504.huracan.repository.PurchaseRepository;
import com.sc504.huracan.security.SystemUserDetails;
import com.sc504.huracan.security.service.SecurityService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

  private final PurchaseRepository purchaseRepository;

  private final SecurityService securityService;

  public PurchaseService(PurchaseRepository purchaseRepository, SecurityService securityService) {
    this.purchaseRepository = purchaseRepository;
    this.securityService = securityService;
  }

  public Long executePurchase(PurchaseRequestDTO purchaseRequestDTO) {

    Integer systemUserDetailsUserId = securityService.getSystemUserDetailsUserId();

    Long purchaseId = purchaseRepository.createPurchase(purchaseRequestDTO.supplierId(),
        systemUserDetailsUserId,
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

    return purchaseId;
  }

  private BigDecimal calculatePurchaseTotal(List<PurchaseDetailDTO> purchaseDetailDTOS) {
    return purchaseDetailDTOS.stream()
        .map(purchaseDetailDTO -> purchaseDetailDTO.price()
            .multiply(BigDecimal.valueOf(purchaseDetailDTO.quantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public PurchaseSummaryDTO getPurchaseSummaryById(Long saleId) {
    return purchaseRepository.getPurchaseSummaryById(saleId);
  }

}
