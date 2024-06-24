package com.minyan.vo;

import com.minyan.Enum.CodeEnum;
import lombok.Data;

/**
 * @create 2024-06-24 10:48
 * @description 项目统一出参
 * @author minyan.he
 */
@Data
public class ApiResult<T> {
  private String code;
  private String message;
  private T data;

  public ApiResult() {}

  public ApiResult(String code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static ApiResult build(String code, String message) {
    return new ApiResult<>(code, message, null);
  }

  public static ApiResult buildSuccess(Object data) {
    return new ApiResult<>(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), data);
  }

  public static ApiResult build(CodeEnum codeEnum) {
    return new ApiResult<>(codeEnum.getCode(), codeEnum.getMessage(), null);
  }
}
