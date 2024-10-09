package com.minyan.currencycapi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.minyan.Enum.OrderConfimTagEnum;
import com.minyan.currencycapi.handler.confirm.fail.OrderConfirmFailHandler;
import com.minyan.currencycapi.handler.confirm.success.OrderConfirmSuccessHandler;
import com.minyan.currencycapi.service.OrderService;
import com.minyan.param.OrderConfirmParam;
import com.minyan.vo.context.ConfirmContext;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @decription 订单相关操作处理service
 * @author minyan.he
 * @date 2024/9/1 10:31
 */
@Service
public class OrderServiceImpl implements OrderService {
  public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

  @Autowired List<OrderConfirmSuccessHandler> orderConfirmSuccessHandlerList;
  @Autowired List<OrderConfirmFailHandler> orderConfirmFailHandlerList;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean confirmOrder(OrderConfirmParam param) {
    ConfirmContext confirmContext = new ConfirmContext();
    confirmContext.setParam(param);
    switch (Objects.requireNonNull(OrderConfimTagEnum.getByValue(param.getConfirmTag()))) {
      case CONFIRM_SUCCESS:
        List<OrderConfirmSuccessHandler> successFallBackHandlers = Lists.newArrayList();
        for (OrderConfirmSuccessHandler orderConfirmSuccessHandler :
            orderConfirmSuccessHandlerList) {
          successFallBackHandlers.add(orderConfirmSuccessHandler);
          if (!orderConfirmSuccessHandler.handle(confirmContext)) {
            // 失败执行回退操作
            for (OrderConfirmSuccessHandler orderConfirmSuccessFallbackHandler :
                successFallBackHandlers) {
              try {
                orderConfirmSuccessFallbackHandler.fallBack(confirmContext);
              } catch (Exception e) {
                logger.info(
                    "[OrderServiceImpl][confirmOrder]代币确认成功回退异常，请求参数：{}，当前处理handler：{}",
                    JSONObject.toJSONString(confirmContext),
                    successFallBackHandlers.getClass().getName());
              }
            }
            return false;
          }
        }
        break;
      case CONFIRM_FAIL:
        List<OrderConfirmFailHandler> failFallBackHandlers = Lists.newArrayList();
        for (OrderConfirmFailHandler orderConfirmFailHandler : orderConfirmFailHandlerList) {
          failFallBackHandlers.add(orderConfirmFailHandler);
          if (!orderConfirmFailHandler.handle(confirmContext)) {
            // 失败执行回退操作
            for (OrderConfirmFailHandler orderConfirmFailFallbackHandler : failFallBackHandlers) {
              try {
                orderConfirmFailFallbackHandler.fallBack(confirmContext);
              } catch (Exception e) {
                logger.info(
                    "[OrderServiceImpl][confirmOrder]代币发放失败回退异常，请求参数：{}，当前处理handler：{}",
                    JSONObject.toJSONString(confirmContext),
                    orderConfirmFailFallbackHandler.getClass().getName());
              }
            }
            return false;
          }
        }
        break;
      default:
        logger.info(
            "[OrderServiceImpl][confirmOrder]代币确认失败，标识传入有误，请求参数：{}",
            JSONObject.toJSONString(param));
        return false;
    }
    return true;
  }
}
