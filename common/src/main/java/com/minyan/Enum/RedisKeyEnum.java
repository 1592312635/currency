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
  // 业务锁
  ACCOUNT_INFO("account:info:", "账户余额信息"),

  // redis锁
  CURRENCY_ORDER_EXPIRE_REDIS_KEY("currency:order:expire:", "过期订单锁");

  private final String key;
  private final String desc;
}
