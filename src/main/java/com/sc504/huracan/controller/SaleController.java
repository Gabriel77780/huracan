package com.sc504.huracan.controller;

import com.sc504.huracan.api.ApiResponseDTO;
import com.sc504.huracan.dto.SaleRequestDTO;
import com.sc504.huracan.dto.SaleSummaryDTO;
import com.sc504.huracan.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {

  private final SaleService saleService;

  public SaleController(SaleService saleService) {
    this.saleService = saleService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseDTO> executeSale(
      @RequestBody SaleRequestDTO saleRequestDTO) {
    Long saleId = saleService.executeSale(saleRequestDTO);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Venta realizada correctamente", saleId));
  }

  @GetMapping("/summary/{saleId}")
  public ResponseEntity<ApiResponseDTO> getSaleSummaryById(@PathVariable Long saleId) {

    SaleSummaryDTO saleSummaryDTO = saleService.getSaleSummaryById(saleId);

    return ResponseEntity.ok(new ApiResponseDTO(
        true,
        HttpStatus.OK.value(),
        "Resumen de la venta obtenido correctamente",
        saleSummaryDTO
    ));

  }

}
