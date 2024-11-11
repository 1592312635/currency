package com.minyan.currencycapi.aspect;

import com.minyan.Enum.CodeEnum;
import com.minyan.exception.CustomException;
import com.minyan.vo.ApiResult;
import java.lang.reflect.UndeclaredThrowableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @decription 异常处理切面
 * @author minyan.he
 * @date 2024/7/1 11:45
 */
@RestControllerAdvice
public class CustomExceptionHandler {

  // 处理自定义异常类型
  @ResponseBody
  @ExceptionHandler(CustomException.class)
  public ApiResult<?> handleCustomException(CustomException customException) {
    return new ApiResult<>(customException.getCode(), customException.getMessage(), null);
  }

  // 处理自定义异常类型
  @ResponseBody
  @ExceptionHandler(UndeclaredThrowableException.class)
  public ApiResult<?> handleOtherException(UndeclaredThrowableException ex) {
    Throwable undeclaredThrowable = ex.getUndeclaredThrowable();
    if (undeclaredThrowable instanceof CustomException) {
      CustomException customException = (CustomException) undeclaredThrowable;
      return new ApiResult<>(customException.getCode(), customException.getMessage(), null);
    } else {
      return new ApiResult<>(CodeEnum.FAIL.getCode(), undeclaredThrowable.getMessage(), null);
    }
  }

  // 处理其他未被捕获的异常
  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ApiResult<?> handleGeneralException(Exception ex) {
    return new ApiResult<>(CodeEnum.FAIL.getCode(), ex.getMessage(), null);
  }
}
