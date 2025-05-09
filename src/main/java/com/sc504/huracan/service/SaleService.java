package com.sc504.huracan.service;

import com.sc504.huracan.dto.SaleSummaryDTO;
import com.sc504.huracan.exception.SystemException;
import com.sc504.huracan.repository.SaleRepository;
import com.sc504.huracan.dto.SaleDetailDTO;
import com.sc504.huracan.dto.SaleRequestDTO;
import com.sc504.huracan.security.service.SecurityService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

  private final SaleRepository saleRepository;
  
  private final ProductService productService;

  private final SecurityService securityService;

  public SaleService(SaleRepository saleRepository, ProductService productService,
      SecurityService securityService) {
    this.saleRepository = saleRepository;
    this.productService = productService;
    this.securityService = securityService;
  }

  public Long executeSale(SaleRequestDTO saleRequestDTO) {

    validateProductAvailability(saleRequestDTO);

    Integer systemUserDetailsUserId = securityService.getSystemUserDetailsUserId();

    Long saleId = saleRepository.createSale(saleRequestDTO.customerId(), systemUserDetailsUserId,
        calculateSaleTotal(saleRequestDTO.saleDetailDTOS()));

    for (SaleDetailDTO saleDetailDTO : saleRequestDTO.saleDetailDTOS()) {
      saleRepository.createSaleDetail(
          saleId,
          saleDetailDTO.productId(),
          saleDetailDTO.quantity(),
          saleDetailDTO.price(),
          saleDetailDTO.price()
              .multiply(BigDecimal.valueOf(saleDetailDTO.quantity()))
      );
    }

    return saleId;
  }

  private void validateProductAvailability(SaleRequestDTO saleRequestDTO) {
    saleRequestDTO.saleDetailDTOS().stream()
        .filter(saleDetailDTO ->
            !productService.isProductAvailable(saleDetailDTO.productId()))
        .findFirst()
        .ifPresent(saleDetailDTO -> {
          throw new SystemException("El producto no está disponible");
        });
  }

  private BigDecimal calculateSaleTotal(List<SaleDetailDTO> saleDetailDTOS) {
    return saleDetailDTOS.stream()
        .map(saleDetailDTO -> saleDetailDTO.price()
            .multiply(BigDecimal.valueOf(saleDetailDTO.quantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public SaleSummaryDTO getSaleSummaryById(Long saleId) {
    return saleRepository.getSaleSummaryById(saleId);
  }

}
