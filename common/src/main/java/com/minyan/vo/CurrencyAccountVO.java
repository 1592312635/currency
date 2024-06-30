package com.minyan.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author minyan.he
 * @decription
 * @date 2024/6/24 11:59
 */
@Data
public class CurrencyAccountVO {
  private BigDecimal currency;
  private Integer currencyType;

  public CurrencyAccountVO() {}

  public CurrencyAccountVO(BigDecimal currency, Integer currencyType) {
    this.currency = currency;
    this.currencyType = currencyType;
  }
}
