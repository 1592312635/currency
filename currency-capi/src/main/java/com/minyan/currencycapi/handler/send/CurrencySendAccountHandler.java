package com.minyan.currencycapi.handler.send;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.minyan.Enum.CodeEnum;
import com.minyan.dao.CurrencyAccountMapper;
import com.minyan.exception.CustomException;
import com.minyan.param.AccountSendParam;
import com.minyan.po.CurrencyAccountPO;
import com.minyan.vo.send.SendContext;
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
@Order(30)
public class CurrencySendAccountHandler extends CurrencySendAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencySendAccountHandler.class);

  @Autowired private CurrencyAccountMapper currencyAccountMapper;

  @SneakyThrows(CustomException.class)
  @Override
  public boolean handle(SendContext sendContext) {
    AccountSendParam param = sendContext.getParam();
    // 查询是否存在代币账户
    Map<String, Object> queryMap = buildQueryCurrencyAccountPO(param);
    List<CurrencyAccountPO> currencyAccountPOS =
        currencyAccountMapper.selectListSelective(queryMap);

    int count = 0;
    if (CollectionUtils.isEmpty(currencyAccountPOS)) {
      CurrencyAccountPO currencyAccountPO = buildInsertCurrencyAccountPO(param);
      count = currencyAccountMapper.insertSelective(currencyAccountPO);
    } else {
      count =
          currencyAccountMapper.updateByUserIdAndCurrencyType(
              param.getUserId(), param.getCurrencyType(), param.getAddCurrency());
    }
    logger.info(
        "[CurrencySendAccountHandler][handle]代币发放结束，请求参数：{}，账户信息：{}，返回结果：{}",
        JSONObject.toJSONString(param),
        JSONObject.toJSONString(currencyAccountPOS),
        count);
    if (count <= 0) {
      throw new CustomException(CodeEnum.ACCOUNT_UPDATE_FAIL);
    }
    return true;
  }

  /**
   * 新建代币账户请求参数
   *
   * @param param
   * @return
   */
  CurrencyAccountPO buildInsertCurrencyAccountPO(AccountSendParam param) {
    CurrencyAccountPO currencyAccountPO = new CurrencyAccountPO();
    currencyAccountPO.setUserId(param.getUserId());
    currencyAccountPO.setCurrency(param.getAddCurrency());
    currencyAccountPO.setCurrencyType(param.getCurrencyType());
    return currencyAccountPO;
  }

  /**
   * 查询代币账户信息请求参数
   *
   * @param param
   * @return
   */
  Map<String, Object> buildQueryCurrencyAccountPO(AccountSendParam param) {
    Map<String, Object> map = Maps.newHashMap();
    map.put("userId", param.getUserId());
    map.put("currencyType", param.getCurrencyType());
    return map;
  }
}
