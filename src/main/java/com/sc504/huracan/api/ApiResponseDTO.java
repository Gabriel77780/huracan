package com.sc504.huracan.api;

public record ApiResponseDTO(boolean success, int status, String message, Object data) {

  public ApiResponseDTO(boolean success, int status, String message) {
    this(success, status, message, null);
  }
}