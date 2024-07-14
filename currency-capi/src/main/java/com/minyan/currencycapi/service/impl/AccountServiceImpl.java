package com.minyan.currencycapi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minyan.Enum.RedisKeyEnum;
import com.minyan.currencycapi.handler.deduct.CurrencyDeductHandler;
import com.minyan.currencycapi.handler.send.CurrencySendHandler;
import com.minyan.currencycapi.service.AccountService;
import com.minyan.dao.CurrencyAccountMapper;
import com.minyan.param.AccountDeductParam;
import com.minyan.param.AccountQueryParam;
import com.minyan.param.AccountSendParam;
import com.minyan.po.CurrencyAccountPO;
import com.minyan.vo.CurrencyAccountVO;
import com.minyan.vo.deduct.DeductContext;
import com.minyan.vo.send.SendContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/24 14:36
 */
@Service
public class AccountServiceImpl implements AccountService {
  private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

  @Autowired List<CurrencySendHandler> currencySendHandlers;
  @Autowired List<CurrencyDeductHandler> currencyDeductHandlers;
  @Autowired private CurrencyAccountMapper currencyAccountMapper;

//  @Cacheable(value = "getAccount", key = "#param.userId + ':' + #param.currencyType")
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

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean send(AccountSendParam param) {
    SendContext sendContext = new SendContext();
    sendContext.setParam(param);
    List<CurrencySendHandler> fallBackHandlers = Lists.newArrayList();
    for (CurrencySendHandler currencySendHandler : currencySendHandlers) {
      fallBackHandlers.add(currencySendHandler);
      if (!currencySendHandler.handle(sendContext)) {
        // 失败执行回退操作
        for (CurrencySendHandler fallBackHandler : fallBackHandlers) {
          try {
            fallBackHandler.fallBack(sendContext);
          } catch (Exception e) {
            logger.info(
                "[AccountServiceImpl][send]代币发放失败回退异常，请求参数：{}，当前处理handler：{}",
                JSONObject.toJSONString(sendContext),
                fallBackHandler.getClass().getName());
          }
        }
        return false;
      }
    }
    return true;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean deduct(AccountDeductParam param) {
    DeductContext deductContext = new DeductContext();
    deductContext.setParam(param);
    List<CurrencyDeductHandler> fallBackHandlers = Lists.newArrayList();
    for (CurrencyDeductHandler currencyDeductHandler : currencyDeductHandlers) {
      fallBackHandlers.add(currencyDeductHandler);
      if (!currencyDeductHandler.handle(deductContext)) {
        // 失败执行回退操作
        for (CurrencyDeductHandler fallBackHandler : fallBackHandlers) {
          try {
            fallBackHandler.fallBack(deductContext);
          } catch (Exception e) {
            logger.info(
                "[AccountServiceImpl][deduct]代币扣减失败回退异常，请求参数：{}，当前处理handler：{}",
                JSONObject.toJSONString(deductContext),
                fallBackHandler.getClass().getName());
          }
        }
        return false;
      }
    }
    return true;
  }
}
