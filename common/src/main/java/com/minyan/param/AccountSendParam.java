package com.minyan.param;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @decription 代币发放请求参数
 * @author minyan.he
 * @date 2024/6/25 18:38
 */
@Data
public class AccountSendParam {
  @NotBlank(message = "用户信息不能为空")
  private String userId;
  @NotNull(message = "发放金额不能为空")
  private BigDecimal addCurrency;
  @NotNull(message = "代币类型不能为空")
  private Integer currencyType;
  @NotBlank(message = "流水号不能为空")
  private String businessId;
  private String behaviorCode;
  private String behaviorDesc;
}
