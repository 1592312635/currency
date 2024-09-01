package com.minyan.currencycapi.controller;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.currencycapi.service.OrderService;
import com.minyan.param.OrderConfirmParam;
import com.minyan.vo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @decription 订单相关处理
 * @author minyan.he
 * @date 2024/9/1 10:16
 */
@RestController
@RequestMapping("/order")
public class OrderController {
  public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
  @Autowired private OrderService orderService;

  @RequestMapping("/confirm")
  public ApiResult<Boolean> confirmOrder(@Validated @RequestBody OrderConfirmParam param) {
    logger.info("[OrderController][confirmOrder]代币订单确认时，请求参数：{}", JSONObject.toJSONString(param));
    return ApiResult.buildSuccess(orderService.confirmOrder(param));
  }
}
