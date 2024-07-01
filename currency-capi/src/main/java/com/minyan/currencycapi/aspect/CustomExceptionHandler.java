package com.minyan.currencycapi.aspect;

import com.minyan.Enum.CodeEnum;
import com.minyan.exception.CurrencyException;
import com.minyan.vo.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @decription 异常处理切面
 * @author minyan.he
 * @date 2024/7/1 11:45
 */
@ControllerAdvice
public class CustomExceptionHandler {

  // 处理自定义异常类型
  @ExceptionHandler(value = {CurrencyException.class})
  public ApiResult<?> handleCurrencyException(CurrencyException ex) {
    return new ApiResult<>(ex.getCode(), ex.getMessage(), null);
  }

  // 处理其他未被捕获的异常
  @ExceptionHandler(value = {Exception.class})
  public ApiResult<?> handleGeneralException(Exception ex) {
    return new ApiResult<>(CodeEnum.FAIL.getCode(), ex.getMessage(), null);
  }
}
