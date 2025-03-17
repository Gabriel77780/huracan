package com.sc504.huracan.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Customer {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private Timestamp createdAt;

}
