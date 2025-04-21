package com.sc504.huracan.security.service;

import com.sc504.huracan.exception.SystemException;
import com.sc504.huracan.security.SystemUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  public Integer getSystemUserDetailsUserId() {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    Integer systemUserDetailsUserId;
    if (auth != null && auth.getPrincipal() instanceof SystemUserDetails systemUserDetails) {

      systemUserDetailsUserId = systemUserDetails.getUserId();

    } else {
      throw new SystemException("La sesión ha caducado o no existe,"
          + " por favor inicie sesión nuevamente.");
    }

    return systemUserDetailsUserId;
  }

}
