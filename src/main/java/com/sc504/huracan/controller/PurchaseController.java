package com.sc504.huracan.controller;


import com.sc504.huracan.api.ApiResponseDTO;
import com.sc504.huracan.dto.PurchaseRequestDTO;
import com.sc504.huracan.dto.PurchaseSummaryDTO;
import com.sc504.huracan.dto.SaleSummaryDTO;
import com.sc504.huracan.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

  private final PurchaseService purchaseService;

  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseDTO> executePurchase(
      @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
    Long purchaseId = purchaseService.executePurchase(purchaseRequestDTO);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Compra registrada correctamente", purchaseId));
  }

  @GetMapping("/summary/{purchaseId}")
  public ResponseEntity<ApiResponseDTO> getPurchaseSummaryById(@PathVariable Long purchaseId) {

    PurchaseSummaryDTO purchaseSummaryDTO = purchaseService.getPurchaseSummaryById(purchaseId);

    return ResponseEntity.ok(new ApiResponseDTO(
        true,
        HttpStatus.OK.value(),
        "Resumen de la compra obtenido correctamente",
        purchaseSummaryDTO
    ));

  }

}
