package com.minyan.currencycapi.handler.deduct;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.CodeEnum;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.dao.CurrencySerialMapper;
import com.minyan.exception.CustomException;
import com.minyan.param.AccountDeductParam;
import com.minyan.po.CurrencySerialPO;
import com.minyan.vo.context.DeductContext;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 代币扣减流水生成
 * @author minyan.he
 * @date 2024/7/2 11:10
 */
@Service
@Order(50)
public class CurrencyDeductSerialHandler extends CurrencyDeductAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyDeductSerialHandler.class);

  @Autowired private CurrencySerialMapper currencySerialMapper;

  @SneakyThrows(CustomException.class)
  @Override
  public boolean handle(DeductContext deductContext) {
    AccountDeductParam param = deductContext.getParam();
    CurrencySerialPO currencySerialPO = buildCurrencySerialPO(param);
    int result = currencySerialMapper.insertSelective(currencySerialPO);
    logger.info(
        "[CurrencyDeductSerialHandler][handle]代币扣减生成流水结束，请求参数：{}，返回结果：{}",
        JSONObject.toJSONString(currencySerialPO),
        result);
    if (result <= 0) {
      throw new CustomException(CodeEnum.SERIAL_INSERT_FAIL);
    }
    return true;
  }

  /**
   * 构建代币流水
   *
   * @param param
   * @return
   */
  CurrencySerialPO buildCurrencySerialPO(AccountDeductParam param) {
    CurrencySerialPO currencySerialPO = new CurrencySerialPO();
    currencySerialPO.setBusinessId(param.getBusinessId());
    currencySerialPO.setUserId(param.getUserId());
    currencySerialPO.setBehaviorCode(param.getBehaviorCode());
    currencySerialPO.setBehaviorDesc(param.getBehaviorDesc());
    currencySerialPO.setHandleType(HandleTypeEnum.REDUCE.getValue());
    currencySerialPO.setCurrencyType(param.getCurrencyType());
    currencySerialPO.setAmount(param.getDeductCurrency());
    return currencySerialPO;
  }
}
