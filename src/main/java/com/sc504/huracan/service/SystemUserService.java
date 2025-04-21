package com.sc504.huracan.service;

import com.sc504.huracan.model.SystemUser;
import com.sc504.huracan.repository.SystemUserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService {

  private final SystemUserRepository systemUserRepository;

  public SystemUserService(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  public void createSystemUser(SystemUser systemUser) {
    systemUserRepository.createSystemUser(systemUser);
  }

  public SystemUser getSystemUserById(Long id) {
    return systemUserRepository.getSystemUserById(id);
  }

  public void updateSystemUser(SystemUser systemUser) {
    systemUserRepository.updateSystemUser(systemUser);
  }

  public boolean deleteSystemUser(Long id) {
    return systemUserRepository.deleteSystemUser(id);
  }

  public List<SystemUser> getAllSystemUsers() {
    return systemUserRepository.getAllSystemUsers();
  }

}
