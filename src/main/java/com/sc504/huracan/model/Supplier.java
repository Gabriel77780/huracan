package com.sc504.huracan.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Supplier {

  private Long id;
  private String name;
  private String phone;
  private String email;
  private Timestamp createdAt;

}
