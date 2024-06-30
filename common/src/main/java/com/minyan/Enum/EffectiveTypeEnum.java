package com.minyan.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @decription 有效期类型枚举
 * @author minyan.he
 * @date 2024/6/30 17:36
 */
@AllArgsConstructor
@Getter
public enum EffectiveTypeEnum {
  PERMANENT(0, "永久"),
  ABSOLUTE(1, "绝对有效期"),
  RELATIVE(2, "相对有效期"),
  NATURE(3, "自然有效期");

  private Integer value;
  private String desc;
}
