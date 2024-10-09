package com.minyan.vo.context;

import com.minyan.param.AccountDeductParam;
import com.minyan.po.CurrencyRulePO;
import lombok.Data;

/**
 * @decription 扣减中间参数
 * @author minyan.he
 * @date 2024/7/3 10:31
 */
@Data
public class DeductContext {
  /** 请求参数 */
  private AccountDeductParam param;

  /** 代币规则 */
  private CurrencyRulePO currencyRulePO;
}
