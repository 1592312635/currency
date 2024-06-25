package com.minyan.currencycapi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.minyan.currencycapi.service.AccountService;
import com.minyan.dao.CurrencyAccountMapper;
import com.minyan.param.AccountQueryParam;
import com.minyan.po.CurrencyAccountPO;
import com.minyan.vo.CurrencyAccountVO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/24 14:36
 */
@Service
public class AccountServiceImpl implements AccountService {
  private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

  @Autowired private CurrencyAccountMapper currencyAccountMapper;

  @Override
  public CurrencyAccountVO getAccount(AccountQueryParam param) {
    Map<String, Object> map = Maps.newHashMap();
    List<CurrencyAccountPO> currencyAccountPOS = currencyAccountMapper.selectListSelective(map);
    logger.info(
        "[AccountServiceImpl][getAccount]查询代币余额信息请求结束，请求参数：{}，返回结果：{}",
        JSONObject.toJSONString(param),
        JSONObject.toJSONString(currencyAccountPOS));
    if (!CollectionUtils.isEmpty(currencyAccountPOS)) {
      return new CurrencyAccountVO(
          currencyAccountPOS.get(0).getCurrency(), currencyAccountPOS.get(0).getCurrencyType());
    }
    return new CurrencyAccountVO(BigDecimal.ZERO, param.getCurrencyType());
  }
}
