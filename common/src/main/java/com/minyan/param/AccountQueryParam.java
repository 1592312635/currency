package com.minyan.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author minyan.he
 * @decription 查询代币余额信息参数
 * @date 2024/6/24 12:16
 */
@Data
public class AccountQueryParam {
  @NotBlank(message = "userId不能为空")
  private String userId;

  @NotNull(message = "代币类型不能为空")
  private Integer currencyType;
}
