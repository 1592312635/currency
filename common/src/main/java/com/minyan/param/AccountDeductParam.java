package com.minyan.param;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @decription 代币扣减请求参数
 * @author minyan.he
 * @date 2024/7/2 12:12
 */
@Data
public class AccountDeductParam {
  @NotBlank(message = "用户信息不能为空")
  private String userId;
  @NotNull(message = "扣减金额不能为空")
  private BigDecimal deductCurrency;
  @NotNull(message = "代币类型不能为空")
  private Integer currencyType;
  @NotBlank(message = "流水号不能为空")
  private String businessId;
  private String behaviorCode;
  private String behaviorDesc;
}
