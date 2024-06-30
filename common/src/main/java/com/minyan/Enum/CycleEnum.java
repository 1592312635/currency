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
    DAY(1, "日"),
    WEEK(2, "周"),
    MONTH(3, "月"),
    YEAR(4, "年");

    private final Integer code;
    private final String desc;
}
