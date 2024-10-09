package com.minyan.vo.context;

import com.minyan.param.AccountSendParam;
import com.minyan.po.CurrencyRulePO;
import lombok.Data;

/**
 * @decription 代币发放中间变量
 * @author minyan.he
 * @date 2024/6/25 18:36
 */
@Data
public class SendContext {
  /** 请求参数 */
  private AccountSendParam param;

  /** 代币规则 */
  private CurrencyRulePO currencyRulePO;
}
