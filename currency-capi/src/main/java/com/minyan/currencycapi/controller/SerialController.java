package com.minyan.currencycapi.controller;

import com.minyan.currencycapi.service.SerialService;
import com.minyan.param.SerialQueryParam;
import com.minyan.vo.ApiResult;
import com.minyan.vo.CurrencySerialVO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/3 13:24
 */
@RestController
@RequestMapping("/serial")
public class SerialController {
  private static final Logger logger = LoggerFactory.getLogger(SerialController.class);
  @Autowired private SerialService serialService;

  @RequestMapping("/query")
  public ApiResult<List<CurrencySerialVO>> querySerial(
      @RequestBody @Validated SerialQueryParam param) {
    return ApiResult.buildSuccess(serialService.querySerial(param));
  }
}
