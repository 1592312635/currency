package com.minyan.currencycapi.proxy;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.param.AccountQueryParam;
import com.minyan.vo.ApiResult;
import com.minyan.vo.CurrencyAccountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
  private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

  @RequestMapping("/getAccount")
  public ApiResult<CurrencyAccountVO> getAccount(@RequestBody AccountQueryParam param) {
    try {
      CurrencyAccountVO account = new CurrencyAccountVO();
      logger.info(
          "[AccountController][getAccount]查询代币账户信息完成，请求参数：{}，返回结果：{}",
          JSONObject.toJSONString(param),
          JSONObject.toJSONString(account));
      return ApiResult.buildSuccess(account);
    } catch (Exception e) {
      logger.error("[AccountController][getAccount]查询代币账户信息时报错", e);
      return ApiResult.build("10000", "fail");
    }
  }
}
