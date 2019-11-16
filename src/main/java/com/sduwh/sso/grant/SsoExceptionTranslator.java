package com.sduwh.sso.grant;

import com.sduwh.sso.constant.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import java.security.InvalidParameterException;

/**
 * 异常转换器, 用于oauth2.0授权
 *
 * @author wxp
 */
public class SsoExceptionTranslator implements WebResponseExceptionTranslator {
  @Override
  public ResponseEntity<OAuth2Exception> translate(Exception err) throws Exception {
    if (err instanceof InvalidParameterException) {
      return ResponseEntity.ok(translateInvalidParamException((InvalidParameterException) err));
    } else if (err instanceof InvalidRequestException) {
      return ResponseEntity.ok(translateInvalidRequestException((InvalidRequestException) err));
    } else if (err instanceof UnsupportedGrantTypeException) {
      return ResponseEntity.ok(
          translateUnsupportedGrantTypeException((UnsupportedGrantTypeException) err));
    } else if (err instanceof InvalidGrantException) {
      return ResponseEntity.ok(translateInvalidGrantException((InvalidGrantException) err));
    }
    OAuth2Exception auth2Exception =
        OAuth2Exception.create(OAuth2Exception.ERROR, err.getMessage());
    auth2Exception.addAdditionalInformation("code", "999001");
    return ResponseEntity.ok(auth2Exception);
  }

  private OAuth2Exception translateInvalidParamException(InvalidParameterException err) {
    OAuth2Exception auth2Exception =
        OAuth2Exception.create(
            OAuth2Exception.INVALID_REQUEST, ErrorCode.INPUT_FIELD_INVALID.name());
    auth2Exception.addAdditionalInformation(
        "code", String.valueOf(ErrorCode.INPUT_FIELD_INVALID.getCode()));
    return auth2Exception;
  }

  private OAuth2Exception translateInvalidRequestException(InvalidRequestException err) {
    err.addAdditionalInformation("code", String.valueOf(ErrorCode.INVALID_REQUEST.getCode()));
    return err;
  }

  private OAuth2Exception translateUnsupportedGrantTypeException(
      UnsupportedGrantTypeException err) {
    err.addAdditionalInformation(
        "code", String.valueOf(ErrorCode.UNSUPPORTED_GRANT_TYPE.getCode()));
    return err;
  }

  private OAuth2Exception translateInvalidGrantException(InvalidGrantException err) {
    err.addAdditionalInformation("code", String.valueOf(ErrorCode.INVALID_GRANT.getCode()));
    return err;
  }
}
