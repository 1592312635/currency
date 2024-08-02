package com.minyan.currencycapi.handler.deduct;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.CodeEnum;
import com.minyan.dao.CurrencyIdempotentMapper;
import com.minyan.exception.CustomException;
import com.minyan.param.AccountDeductParam;
import com.minyan.po.CurrencyIdempotentPO;
import com.minyan.vo.context.deduct.DeductContext;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 幂等信息处理handler
 * @author minyan.he
 * @date 2024/7/3 10:52
 */
@Service
@Order(10)
public class CurrencyDeductIdempotentHandler extends CurrencyDeductAbstractHandler {
  private static final Logger logger =
      LoggerFactory.getLogger(CurrencyDeductIdempotentHandler.class);
  @Autowired private CurrencyIdempotentMapper currencyIdempotentMapper;

  @SneakyThrows(CustomException.class)
  @Override
  public boolean handle(DeductContext deductContext) {
    AccountDeductParam param = deductContext.getParam();
    int count = currencyIdempotentMapper.countByBusinessId(param.getBusinessId());
    logger.info(
        "[CurrencyDeductIdempotentHandler][handle]幂等校验结束，请求参数：{}，返回结果：{}",
        JSONObject.toJSONString(param),
        count);
    if (count > 0) {
      logger.info(
          "[CurrencyDeductIdempotentHandler][handle]代币发放幂等性校验不通过，已存在当前流水号信息，请求参数：{}，返回结果：{}",
          JSONObject.toJSONString(param),
          count);
      throw new CustomException(CodeEnum.IDEMPOTENT_EXIST);
    }
    // 构建新的流水幂等信息数据
    CurrencyIdempotentPO currencyIdempotentPO = buildCurrencyIdempotentPO(param);
    int result = currencyIdempotentMapper.insertSelective(currencyIdempotentPO);
    return result > 0;
  }

  CurrencyIdempotentPO buildCurrencyIdempotentPO(AccountDeductParam param) {
    CurrencyIdempotentPO po = new CurrencyIdempotentPO();
    po.setBusinessId(param.getBusinessId());
    po.setBehaviorCode(param.getBehaviorCode());
    return po;
  }
}
