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
public class CustomException extends Exception {

  private String code;
  private String message;

  public CustomException(String message) {
    super(message);
  }

  public CustomException(String message, Throwable cause) {
    super(message, cause);
  }

  public CustomException(CodeEnum codeEnum) {
    this.code = codeEnum.getCode();
    this.message = codeEnum.getMessage();
  }
}
