package com.minyan.currencycrond.http;

import com.minyan.param.SerialQueryParam;
import com.minyan.vo.CurrencySerialVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/4 16:41
 */
@Component
@FeignClient(name = "currencycapi")
public interface CurrencycapiHttp {
    @RequestMapping("/serial/query")
    List<CurrencySerialVO> querySerial(SerialQueryParam param);
}
