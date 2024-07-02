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

    ;
    private String code;
    private String message;
}
