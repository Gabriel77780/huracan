package com.sc504.huracan.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Product {

  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private String category;
  private Timestamp createdAt;

}
