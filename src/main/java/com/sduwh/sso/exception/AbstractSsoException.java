package com.sduwh.sso.exception;

import com.sduwh.sso.constant.ErrorCode;

/**
 * 本项目自定义异常
 *
 * @author wxp
 */
public abstract class AbstractSsoException extends RuntimeException {
  /**
   * 异常的错误码
   *
   * @return 错误码
   */
  public abstract ErrorCode getErrorCode();

  @Override
  public String getMessage() {
    return String.format("%s: %s", getErrorCode().name(), super.getMessage());
  }
}
