package com.minyan.currencycrond.task;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.currencycrond.http.CurrencycapiHttp;
import com.minyan.param.SerialQueryParam;
import com.minyan.vo.CurrencySerialVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @decription 代币过期任务
 * @author minyan.he
 * @date 2024/7/4 14:52
 */
@RestController
public class CurrencyExpireTask {
  private static final Logger logger =
          LoggerFactory.getLogger(CurrencyExpireTask.class);
  @Autowired private CurrencycapiHttp currencycapiHttp;

  @Scheduled(cron = "0/3 * * * * ?")
  public void querySerialCrond() {
    SerialQueryParam serialQueryParam = new SerialQueryParam();
    serialQueryParam.setUserId("555555");
    serialQueryParam.setCurrencyType(1);
    List<CurrencySerialVO> currencySerialVOS = currencycapiHttp.querySerial(serialQueryParam);
    logger.info("[CurrencyExpireTask][querySerialCrond]查询流水结果：{}", JSONObject.toJSONString(currencySerialVOS));
  }
}
