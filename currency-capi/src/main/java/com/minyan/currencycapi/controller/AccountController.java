package com.minyan.currencycapi.controller;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.CodeEnum;
import com.minyan.currencycapi.service.AccountService;
import com.minyan.param.AccountQueryParam;
import com.minyan.param.AccountSendParam;
import com.minyan.vo.ApiResult;
import com.minyan.vo.CurrencyAccountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
  private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
  @Autowired private AccountService accountService;

  @RequestMapping("/get")
  public ApiResult<CurrencyAccountVO> getAccount(@RequestBody @Validated AccountQueryParam param) {
    try {
      CurrencyAccountVO account = accountService.getAccount(param);
      logger.info(
          "[AccountController][getAccount]查询代币账户信息完成，请求参数：{}，返回结果：{}",
          JSONObject.toJSONString(param),
          JSONObject.toJSONString(account));
      return ApiResult.buildSuccess(account);
    } catch (Exception e) {
      logger.error("[AccountController][getAccount]查询代币账户信息时报错", e);
      return ApiResult.build(CodeEnum.FAIL.getCode(), e.getMessage());
    }
  }

  @RequestMapping("/send")
  public ApiResult<Boolean> send(@RequestBody @Validated AccountSendParam param) {
    try{
      return ApiResult.build(CodeEnum.SUCCESS);
    }catch (Exception e){
      logger.error("[AccountController][send]代币发送时异常", e);
      return ApiResult.build(CodeEnum.FAIL.getCode(), e.getMessage());
    }
  }
}
