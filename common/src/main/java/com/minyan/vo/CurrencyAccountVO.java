package com.minyan.vo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author minyan.he
 * @decription
 * @date 2024/6/24 11:59
 */
@Data
@AllArgsConstructor
public class CurrencyAccountVO {
  private BigDecimal currency;
  private Integer currencyType;

  public CurrencyAccountVO() {}
}
