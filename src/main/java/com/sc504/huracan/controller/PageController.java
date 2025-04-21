package com.sc504.huracan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @GetMapping("/homeFragment")
  public String getHomeFragment() {
    return "home :: content";
  }

  @GetMapping("/aboutFragment")
  public String getAboutFragment() {
    return "about :: content";
  }

  @GetMapping("/readmeFragment")
  public String getReadmeFragment() {
    return "readme :: content";
  }

  @GetMapping("/dashboardFragment")
  public String getDashboardFragment() {
    return "dashboard :: content";
  }

  @GetMapping("/productFragment")
  public String getProductFragment() {
    return "product :: content";
  }

  @GetMapping("/customerFragment")
  public String getCustomerFragment() {
    return "customer :: content";
  }

  @GetMapping("/saleFragment")
  public String getClientSaleFragment() {
    return "sale :: content";
  }

  @GetMapping("/supplierFragment")
  public String getSupplierFragment() {
    return "supplier :: content";
  }

}
