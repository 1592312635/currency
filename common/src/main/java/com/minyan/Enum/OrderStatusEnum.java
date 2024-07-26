package com.minyan.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/15 12:44
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
  DEFAULT(0, "待处理"),
  SUCCESS(1, "成功"),
  FAIL(2, "失败"),
  EXPIRE(3, "过期"),
  DEDUCT(4, "已抵扣");

  private final Integer value;
  private final String desc;
}
