package com.minyan.vo;

import java.util.Date;
import lombok.Data;

/**
 * @decription 代币规则信息
 * @author minyan.he
 * @date 2024/6/30 17:26
 */
@Data
public class CurrencyRuleVO {
  /** 代币类型 */
  private Integer currencyType;

  /** 代币名称 */
  private String currencyTypeDesc;

  /** 有效期类型-EffectiveTypeEnum */
  private Integer effectiveType;

  /** 生效周期类型-CycleEnum */
  private Integer effectiveCycle;

  /** 生效周期时长 */
  private Integer effectiveSpan;

  /** 失效周期类型-CycleEnum */
  private Integer expireCycle;

  /** 失效周期时长 */
  private Integer expireSpan;

  /** 开始时间 */
  private Date startTime;

  /** 失效时间 */
  private Date endTime;
}
