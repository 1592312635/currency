package com.minyan.currencycapi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.minyan.currencycapi.service.SerialService;
import com.minyan.dao.CurrencySerialMapper;
import com.minyan.param.SerialQueryParam;
import com.minyan.po.CurrencySerialPO;
import com.minyan.vo.CurrencySerialVO;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/3 13:35
 */
@Service
public class SerialServiceImpl implements SerialService {
  private static final Logger logger = LoggerFactory.getLogger(SerialServiceImpl.class);
  @Autowired private CurrencySerialMapper currencySerialMapper;

  @Override
  public List<CurrencySerialVO> querySerial(SerialQueryParam param) {
    List<CurrencySerialVO> currencySerialVOS = Lists.newArrayList();
    List<CurrencySerialPO> currencySerialPOS =
        currencySerialMapper.querySerial(
            param.getUserId(),
            param.getCurrencyType(),
            param.getHandleType(),
            param.getPageSize() * (param.getPageNum() - 1),
            param.getPageSize());
    logger.info(
        "[SerialServiceImpl][querySerial]查询代币流水结束，请求参数：{}，返回结果：{}",
        JSONObject.toJSONString(param),
        JSONObject.toJSONString(currencySerialPOS));
    if (!CollectionUtils.isEmpty(currencySerialPOS)) {
      currencySerialVOS =
          currencySerialPOS.stream()
              .map(this::convertToCurrencySerialVO)
              .collect(Collectors.toList());
    }
    return currencySerialVOS;
  }

  /**
   * PO转化VO
   *
   * @param currencySerialPO
   * @return
   */
  CurrencySerialVO convertToCurrencySerialVO(CurrencySerialPO currencySerialPO) {
    CurrencySerialVO currencySerialVO = new CurrencySerialVO();
    currencySerialVO.setUserId(currencySerialPO.getUserId());
    currencySerialVO.setBehaviorCode(currencySerialPO.getBehaviorCode());
    currencySerialVO.setBehaviorDesc(currencySerialPO.getBehaviorDesc());
    currencySerialVO.setCurrencyType(currencySerialPO.getCurrencyType());
    currencySerialVO.setHandleType(currencySerialPO.getHandleType());
    currencySerialVO.setAmount(currencySerialPO.getAmount());
    currencySerialVO.setCreateTime(currencySerialPO.getCreateTime());
    return currencySerialVO;
  }
}
