package com.minyan.currencycapi.handler.deduct;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.CodeEnum;
import com.minyan.dao.CurrencyAccountMapper;
import com.minyan.exception.CustomException;
import com.minyan.param.AccountDeductParam;
import com.minyan.po.CurrencyAccountPO;
import com.minyan.vo.context.deduct.DeductContext;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @decription 代币发放账户处理handler
 * @author minyan.he
 * @date 2024/6/25 13:45
 */
@Service
@Order(40)
public class CurrencyDeductAccountHandler extends CurrencyDeductAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyDeductAccountHandler.class);

  @Autowired private CurrencyAccountMapper currencyAccountMapper;

  @SneakyThrows(CustomException.class)
  @Override
  public boolean handle(DeductContext deductContext) {
    AccountDeductParam param = deductContext.getParam();
    // 查询代币账户
    CurrencyAccountPO currencyAccountPO =
        currencyAccountMapper.selectByUserIdAndCurrencyType(
            param.getUserId(), param.getCurrencyType());

    // 账户存在校验
    if (ObjectUtils.isEmpty(currencyAccountPO)) {
      throw new CustomException(CodeEnum.ACCOUNT_NOT_EXIST);
    }
    // 账户余额校验
    if (currencyAccountPO.getCurrency().compareTo(param.getDeductCurrency()) < 0) {
      throw new CustomException(CodeEnum.ACCOUNT_NOT_ENOUGH);
    }

    int count =
        currencyAccountMapper.updateByUserIdAndCurrencyType(
            param.getUserId(), param.getCurrencyType(), null, param.getDeductCurrency());
    logger.info(
        "[CurrencyDeductAccountHandler][handle]代币账户扣减结束，请求参数：{}，账户信息：{}，返回结果：{}",
        JSONObject.toJSONString(param),
        JSONObject.toJSONString(currencyAccountPO),
        count);
    if (count <= 0) {
      throw new CustomException(CodeEnum.ACCOUNT_UPDATE_FAIL);
    }
    return true;
  }
}
