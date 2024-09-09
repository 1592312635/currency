package com.minyan.param;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @decription
 * @author minyan.he
 * @date 2024/9/1 10:23
 */
@Data
public class OrderConfirmParam {
  @NotBlank(message = "用户注册id不能为空")
  private String userId;

  @NotBlank(message = "订单号不能为空")
  private String orderNo;

  private Integer currencyType;

  private Integer confirmTag;
}
