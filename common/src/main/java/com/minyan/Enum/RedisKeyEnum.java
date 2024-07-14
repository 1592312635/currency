package com.minyan.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @decription 缓存key
 * @author minyan.he
 * @date 2024/7/8 17:28
 */
@AllArgsConstructor
@Getter
public enum RedisKeyEnum {
  ACCOUNT_INFO("account:info:", "账户余额信息"),
  ;

  private final String key;
  private final String desc;
}
