package com.minyan.po;

import java.util.Date;
import lombok.Data;

/**
 * @decription 幂等信息实体
 * @author minyan.he
 * @date 2024/6/25 18:29
 */
@Data
public class CurrencyIdempotentPO {
  private Long id;
  private String businessId;
  private String behaviorCode;
  private Date createTime;
  private Date updateTime;
  private Integer delTag;
}
