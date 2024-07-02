package com.minyan.po;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @decription 代币流水
 * @author minyan.he
 * @date 2024/7/2 11:01
 */
@Data
public class CurrencySerialPO {
  private Long id;
  private String businessId;
  private String rollbackBusinessId;
  private String userId;
  private String behaviorCode;
  private String behaviorDesc;
  private Integer currencyType;
  private Integer handleType;
  private BigDecimal amount;
  private Date createTime;
  private Date updateTime;
  private Integer delTag;
}
