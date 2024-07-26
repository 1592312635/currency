package com.minyan.po;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @decription 代币订单
 * @author minyan.he
 * @date 2024/7/13 23:04
 */
@Data
public class CurrencyOrderPO {
  private Long id;
  private String userId;
  private String orderNo;
  private BigDecimal amount;
  private BigDecimal failAmount;
  private BigDecimal expireAmount;
  private Integer status;
  private Integer currencyType;
  private Integer handleType;
  private String behaviorCode;
  private Date createTime;
  private Date updateTime;
  private Integer delTag;
}
