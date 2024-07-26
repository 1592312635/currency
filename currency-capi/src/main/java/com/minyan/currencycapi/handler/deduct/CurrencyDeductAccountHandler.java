package com.minyan.currencycapi.handler.deduct;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.minyan.Enum.CodeEnum;
import com.minyan.dao.CurrencyAccountMapper;
import com.minyan.exception.CustomException;
import com.minyan.param.AccountDeductParam;
import com.minyan.po.CurrencyAccountPO;
import com.minyan.vo.deduct.DeductContext;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    Map<String, Object> queryMap = buildQueryCurrencyAccountPO(param);
    List<CurrencyAccountPO> currencyAccountPOS =
        currencyAccountMapper.selectListSelective(queryMap);

    // 账户存在校验
    if (CollectionUtils.isEmpty(currencyAccountPOS)) {
      throw new CustomException(CodeEnum.ACCOUNT_NOT_EXIST);
    }
    // 账户余额校验
    if (currencyAccountPOS.get(0).getCurrency().compareTo(param.getDeductCurrency()) < 0) {
      throw new CustomException(CodeEnum.ACCOUNT_NOT_ENOUGH);
    }

    int count =
        currencyAccountMapper.updateByUserIdAndCurrencyType(
            param.getUserId(), param.getCurrencyType(), null, param.getDeductCurrency());
    logger.info(
        "[CurrencyDeductAccountHandler][handle]代币账户扣减结束，请求参数：{}，账户信息：{}，返回结果：{}",
        JSONObject.toJSONString(param),
        JSONObject.toJSONString(currencyAccountPOS),
        count);
    if (count <= 0) {
      throw new CustomException(CodeEnum.ACCOUNT_UPDATE_FAIL);
    }
    return true;
  }

  /**
   * 查询代币账户信息请求参数
   *
   * @param param
   * @return
   */
  Map<String, Object> buildQueryCurrencyAccountPO(AccountDeductParam param) {
    Map<String, Object> map = Maps.newHashMap();
    map.put("userId", param.getUserId());
    map.put("currencyType", param.getCurrencyType());
    return map;
  }
}
