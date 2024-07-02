package com.minyan.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @decription 处理类型
 * @author minyan.he
 * @date 2024/7/2 11:18
 */
@AllArgsConstructor
@Getter
public enum HandleTypeEnum {
  ADD(1, "增加"),
  REDUCE(2, "减少");

  private Integer value;
  private String desc;
}
