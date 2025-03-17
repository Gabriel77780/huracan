package com.sc504.huracan.model;

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
  private double price;
  private int stock;
  private String category;
  private Timestamp createdAt;

}
