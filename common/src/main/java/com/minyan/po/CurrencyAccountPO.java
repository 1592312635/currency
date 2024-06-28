package com.minyan.po;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @decription 代币账户
 * @author minyan.he
 * @date 2024/6/25 11:35
 */
@Data
public class CurrencyAccountPO {
  private Long id;
  private String userId;
  private BigDecimal currency;
  private Integer currencyType;
  private Date createTime;
  private Date updateTime;

  public CurrencyAccountPO() {}

  public CurrencyAccountPO(BigDecimal currency, Integer currencyType) {
    this.currency = currency;
    this.currencyType = currencyType;
  }
}
