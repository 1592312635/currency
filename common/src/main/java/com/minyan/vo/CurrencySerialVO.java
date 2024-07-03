package com.minyan.vo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @decription 流水查询出参
 * @author minyan.he
 * @date 2024/7/3 13:36
 */
@Data
public class CurrencySerialVO {
  private String userId;
  private String behaviorCode;
  private String behaviorDesc;
  private Integer currencyType;
  private Integer handleType;
  private BigDecimal amount;
  private Date createTime;
}
