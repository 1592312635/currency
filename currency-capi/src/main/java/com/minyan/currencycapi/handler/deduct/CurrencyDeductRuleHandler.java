package com.minyan.currencycapi.handler.deduct;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.CodeEnum;
import com.minyan.dao.CurrencyRuleMapper;
import com.minyan.exception.CustomException;
import com.minyan.param.AccountDeductParam;
import com.minyan.po.CurrencyRulePO;
import com.minyan.vo.deduct.DeductContext;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @decription 代币规则处理handler
 * @author minyan.he
 * @date 2024/7/3 10:52
 */
@Service
@Order(20)
public class CurrencyDeductRuleHandler extends CurrencyDeductAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyDeductRuleHandler.class);
  @Autowired private CurrencyRuleMapper currencyRuleMapper;

  @SneakyThrows(CustomException.class)
  @Override
  public boolean handle(DeductContext deductContext) {
    AccountDeductParam param = deductContext.getParam();
    CurrencyRulePO currencyRulePO =
        currencyRuleMapper.selectByCurrencyType(param.getCurrencyType());
    if (ObjectUtils.isEmpty(currencyRulePO)) {
      logger.info(
          "[CurrencyDeductRuleHandler][handle]代币发放时未查询到指定代币类型规则，请求参数：{}",
          JSONObject.toJSONString(param));
      throw new CustomException(CodeEnum.CURRENCY_RULE_NOT_EXIST);
    }
    deductContext.setCurrencyRulePO(currencyRulePO);
    return true;
  }
}
