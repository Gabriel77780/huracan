package com.sc504.huracan.security;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
public class SystemUserDetails implements UserDetails {

  private final Integer userId;
  private final String username;
  private final String password;
  private final List<GrantedAuthority> authorities;

}
