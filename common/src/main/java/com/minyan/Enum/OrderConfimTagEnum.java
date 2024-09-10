package com.minyan.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @decription 代币订单确认处理标识
 * @author minyan.he
 * @date 2024/9/1 11:54
 */
@Getter
@AllArgsConstructor
public enum OrderConfimTagEnum {
  CONFIRM_SUCCESS(1, "确认成功"),
  CONFIRM_FAIL(2, "确认失败");

  private final Integer value;
  private final String desc;

  public static OrderConfimTagEnum getByValue(Integer value) {
    for (OrderConfimTagEnum item : values()) {
      if (item.getValue().equals(value)) {
        return item;
      }
    }
    return null;
  }
}
