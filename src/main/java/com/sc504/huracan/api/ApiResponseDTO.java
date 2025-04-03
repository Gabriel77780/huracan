package com.sc504.huracan.api;

public record ApiResponseDTO(boolean success, int status, String message) {}