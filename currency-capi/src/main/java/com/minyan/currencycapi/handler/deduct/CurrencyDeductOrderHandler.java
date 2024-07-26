package com.minyan.currencycapi.handler.deduct;

import com.google.common.collect.Lists;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.Enum.OrderStatusEnum;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.param.AccountDeductParam;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.deduct.DeductContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/14 19:05
 */
@Service
@Order(30)
public class CurrencyDeductOrderHandler extends CurrencyDeductAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyDeductAccountHandler.class);

  @Autowired private CurrencyOrderMapper currencyOrderMapper;

  @Override
  public boolean handle(DeductContext deductContext) {
    AccountDeductParam param = deductContext.getParam();
    // 查询需要抵扣的订单
    List<CurrencyOrderPO> orders = getCurrencyOrderPOS(param);

    // 变更订单抵扣金额和订单状态
    updateOrder(orders, param.getDeductCurrency());
    return true;
  }

  /**
   * 获得需要扣抵扣的订单
   *
   * @param param
   * @return
   */
  List<CurrencyOrderPO> getCurrencyOrderPOS(AccountDeductParam param) {
    List<CurrencyOrderPO> orderPOS = Lists.newArrayList();
    BigDecimal allOrderAmount = BigDecimal.ZERO;
    int pageNum = 0;
    int pageSize = 50;
    while (allOrderAmount.compareTo(param.getDeductCurrency()) < 0) {
      List<CurrencyOrderPO> orderPOSPart =
          currencyOrderMapper.queryOrdersByStatusList(
              param.getUserId(),
              param.getCurrencyType(),
              HandleTypeEnum.ADD.getValue(),
              Arrays.asList(OrderStatusEnum.DEFAULT.getValue(), OrderStatusEnum.SUCCESS.getValue()),
              pageNum,
              pageSize);
      if (CollectionUtils.isEmpty(orderPOSPart)) {
        break;
      }
      orderPOS.addAll(orderPOSPart);
      allOrderAmount =
          allOrderAmount.add(
              orderPOSPart.stream()
                  .map(
                      currencyOrderPO ->
                          currencyOrderPO
                              .getAmount()
                              .subtract(currencyOrderPO.getFailAmount())
                              .subtract(currencyOrderPO.getExpireAmount()))
                  .reduce(BigDecimal.ZERO, BigDecimal::add));
      pageNum += pageSize;
    }
    return orderPOS;
  }

  /**
   * 变更订单抵扣金额（后续迁移到消息总线异步处理）
   *
   * @param orderPOS
   * @param deductAmount
   */
  void updateOrder(List<CurrencyOrderPO> orderPOS, BigDecimal deductAmount) {
    BigDecimal needDeductAmount = deductAmount;
    for (CurrencyOrderPO orderPO : orderPOS) {
      BigDecimal orderNeedDeductAmount =
          orderPO.getAmount().subtract(orderPO.getFailAmount().subtract(orderPO.getExpireAmount()));
      CurrencyOrderPO updateCurrencyOrderPO = new CurrencyOrderPO();
      updateCurrencyOrderPO.setId(orderPO.getId());
      if (needDeductAmount.compareTo(orderNeedDeductAmount) >= 0) {
        // 订单全部抵扣
        updateCurrencyOrderPO.setStatus(OrderStatusEnum.DEDUCT.getValue());
        updateCurrencyOrderPO.setFailAmount(orderNeedDeductAmount);
      } else {
        // 订单部分抵扣
        updateCurrencyOrderPO.setFailAmount(needDeductAmount);
        // 已经全部抵扣完，直接终止
        break;
      }
    }
  }
}
