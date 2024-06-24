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
    ;
    private String code;
    private String message;
}
