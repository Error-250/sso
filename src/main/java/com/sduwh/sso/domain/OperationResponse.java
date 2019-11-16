package com.sduwh.sso.domain;

import lombok.Data;

/**
 * 增删改查操作的通用返回定义
 *
 * @author wxp
 */
@Data
public class OperationResponse {
  private int code;
  private String errorMessage;
}
