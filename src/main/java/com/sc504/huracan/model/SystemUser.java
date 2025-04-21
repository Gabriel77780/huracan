package com.sc504.huracan.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SystemUser {

  private Long id;
  private String name;
  private String email;
  private String password;
  private String role;
  private Timestamp createdAt;

}
