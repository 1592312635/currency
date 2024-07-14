package com.minyan.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @decription 返回值统一code
 * @author minyan.he
 * @date 2024/6/24 13:23
 */
@AllArgsConstructor
@Getter
public enum CodeEnum {
  SUCCESS("200", "成功"),
  FAIL("9999", "失败"),

  IDEMPOTENT_EXIST("100000", "幂等性校验失败"),
  CURRENCY_RULE_NOT_EXIST("100001", "币种规则不存在"),
  ACCOUNT_UPDATE_FAIL("100002", "账户更新失败"),
  ACCOUNT_NOT_EXIST("100003", "账户不存在"),
  ACCOUNT_NOT_ENOUGH("100004", "账户余额不足"),
  SERIAL_INSERT_FAIL("100005", "流水生成失败"),
  ;
  private final String code;
  private final String message;
}
