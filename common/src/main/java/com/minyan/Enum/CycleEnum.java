package com.minyan.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @decription 周期类型枚举
 * @author minyan.he
 * @date 2024/6/30 17:30
 */
@AllArgsConstructor
@Getter
public enum CycleEnum {
  SECONDS(1, "秒"),
  MINUTES(2, "分钟"),
  HOURS(3, "小时"),
  DAY(4, "日"),
  WEEK(5, "周"),
  MONTH(6, "月"),
  YEAR(7, "年");

  private final Integer value;
  private final String desc;

  public static CycleEnum getCycleEnumByValue(Integer value) {
    for (CycleEnum cycleEnum : values()) {
      if (cycleEnum.getValue().equals(value)) {
        return cycleEnum;
      }
    }
    return null;
  }
}
