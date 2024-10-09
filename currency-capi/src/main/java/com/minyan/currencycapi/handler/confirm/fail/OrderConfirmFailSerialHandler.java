package com.minyan.currencycapi.handler.confirm.fail;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.dao.CurrencySerialMapper;
import com.minyan.param.OrderConfirmParam;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.po.CurrencySerialPO;
import com.minyan.utils.SnowFlakeUtil;
import com.minyan.vo.context.ConfirmContext;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 订单确认失败流水处理
 * @author minyan.he
 * @date 2024/9/9 21:16
 */
@Service
@Order(30)
public class OrderConfirmFailSerialHandler extends OrderConfirmFailAbstractHandler {
  Logger logger = LoggerFactory.getLogger(OrderConfirmFailSerialHandler.class);

  @Autowired private CurrencySerialMapper currencySerialMapper;

  @Override
  public boolean handle(ConfirmContext confirmContext) {
    OrderConfirmParam param = confirmContext.getParam();
    CurrencyOrderPO currencyOrderPO = confirmContext.getCurrencyOrderPO();
    BigDecimal failAmount = confirmContext.getFailAmount();
    CurrencySerialPO currencySerialPO = buildSerial(param, currencyOrderPO, failAmount);
    currencySerialMapper.insertSelective(currencySerialPO);
    logger.info(
        "[OrderConfirmFailSerialHandler][handle]订单确认失败流水生成成功，请求参数：{}",
        JSONObject.toJSONString(currencySerialPO));
    return true;
  }

  /**
   * 构建失败流水
   *
   * @param param
   * @param currencyOrderPO
   * @param failAmount
   * @return
   */
  CurrencySerialPO buildSerial(
      OrderConfirmParam param, CurrencyOrderPO currencyOrderPO, BigDecimal failAmount) {
    CurrencySerialPO currencySerialPO = new CurrencySerialPO();
    currencySerialPO.setUserId(param.getUserId());
    currencySerialPO.setBehaviorCode(
        String.format("%s%s", currencyOrderPO.getBehaviorCode(), "_fail"));
    currencySerialPO.setBehaviorDesc("失败回退");
    currencySerialPO.setCurrencyType(param.getCurrencyType());
    currencySerialPO.setHandleType(
        HandleTypeEnum.ADD.getValue().equals(currencyOrderPO.getHandleType())
            ? HandleTypeEnum.REDUCE.getValue()
            : HandleTypeEnum.ADD.getValue());
    currencySerialPO.setAmount(failAmount);
    currencySerialPO.setBusinessId(String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId()));
    return currencySerialPO;
  }
}
