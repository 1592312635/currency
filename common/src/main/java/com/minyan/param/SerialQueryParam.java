package com.minyan.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/3 13:34
 */
@Data
public class SerialQueryParam {
  @NotBlank(message = "用户id不能为空")
  private String userId;

  @NotNull(message = "代币类型不能为空")
  private Integer currencyType;

  private Integer handleType;
  private Integer pageNum = 1;
  private Integer pageSize = 10;
}
