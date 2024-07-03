package com.minyan.currencycapi.service;

import com.minyan.param.SerialQueryParam;
import com.minyan.vo.CurrencySerialVO;
import java.util.List;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/3 13:35
 */
public interface SerialService {
  List<CurrencySerialVO> querySerial(SerialQueryParam param);
}
