package com.sc504.huracan.handler.exception;

import com.sc504.huracan.api.ApiResponseDTO;
import com.sc504.huracan.exception.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ApiResponseDTO> handleStudentNotFoundException(Throwable throwable) {
    return ResponseEntity
        .ok(new ApiResponseDTO(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Ocurrió un error inesperado: " + throwable.getMessage()));
  }

  @ExceptionHandler({SystemException.class})
  public ResponseEntity<ApiResponseDTO> handleSystemException(SystemException systemException) {
    return ResponseEntity
        .ok(new ApiResponseDTO(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),
            systemException.getMessage()));
  }

}
