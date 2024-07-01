package com.minyan.exception;

import com.minyan.Enum.CodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @decription 自定义异常
 * @author minyan.he
 * @date 2024/7/1 12:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CurrencyException extends Exception {

  private String code;
  private String message;

  public CurrencyException(String message) {
    super(message);
  }

  public CurrencyException(String message, Throwable cause) {
    super(message, cause);
  }

  public CurrencyException(CodeEnum codeEnum) {
    this.code = codeEnum.getCode();
    this.message = codeEnum.getMessage();
  }
}
