package com.sc504.huracan.controller;


import com.sc504.huracan.api.ApiResponseDTO;
import com.sc504.huracan.model.SystemUser;
import com.sc504.huracan.service.SystemUserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-user")
public class SystemUserController {

  private final SystemUserService systemUserService;

  public SystemUserController(SystemUserService systemUserService) {
    this.systemUserService = systemUserService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseDTO> createSystemUser(@RequestBody SystemUser systemUser) {
    systemUserService.createSystemUser(systemUser);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Usuario del sistema creado correctamente"));
  }

  @GetMapping("/{id}")
  public SystemUser getSystemUser(@PathVariable Long id) {
    return systemUserService.getSystemUserById(id);
  }

  @PutMapping
  public ResponseEntity<ApiResponseDTO> updateSystemUser(@RequestBody SystemUser systemUser) {
    systemUserService.updateSystemUser(systemUser);
    return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
        "Usuario del sistema actualizado correctamente"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseDTO> deleteSystemUser(@PathVariable Long id) {

    boolean success = systemUserService.deleteSystemUser(id);

    if (success) {
      return ResponseEntity.ok(new ApiResponseDTO(true, HttpStatus.OK.value(),
          "Usuario del sistema eliminado correctamente"));
    }

    return ResponseEntity.ok(new ApiResponseDTO(false, HttpStatus.NOT_FOUND.value(),
        "Usuario del sistema no encontrado"));
  }

  @GetMapping("/all")
  public List<SystemUser> getAllSystemUsers() {
    return systemUserService.getAllSystemUsers();
  }

}
