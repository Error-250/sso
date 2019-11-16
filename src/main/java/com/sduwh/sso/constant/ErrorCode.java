package com.sduwh.sso.constant;

import lombok.Getter;

/**
 * 错误码定义
 *
 * @author wxp
 */
@Getter
public enum ErrorCode {
  /*
  输入参数错误
   */
  INPUT_FIELD_INVALID(101001),
  /*
  资源未找到。 如用户资源未找到
   */
  RESOURCE_NOT_FOUND(103001),
  /*
  SSO异常定义
   */
  INVALID_REQUEST(102001),
  UNSUPPORTED_GRANT_TYPE(102002),
  INVALID_GRANT(102003);

  private int code;

  ErrorCode(int code) {
    this.code = code;
  }
}
