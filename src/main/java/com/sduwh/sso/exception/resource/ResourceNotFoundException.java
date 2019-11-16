package com.sduwh.sso.exception.resource;

import com.sduwh.sso.constant.ErrorCode;
import com.sduwh.sso.exception.AbstractSsoException;

/**
 * 资源不存在异常
 *
 * @author wxp
 */
public class ResourceNotFoundException extends AbstractSsoException {
  private String resourceName;

  public ResourceNotFoundException(String resourceName) {
    this.resourceName = resourceName;
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.RESOURCE_NOT_FOUND;
  }

  @Override
  public String getMessage() {
    return String.format("%s: %s", getErrorCode().name(), resourceName);
  }
}
