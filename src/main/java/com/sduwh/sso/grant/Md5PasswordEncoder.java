package com.sduwh.sso.grant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * Md5加密, 主要用于加密用户密码, 避免明文保存
 *
 * @author wxp
 */
public class Md5PasswordEncoder implements PasswordEncoder {
  @Override
  public String encode(CharSequence originStr) {
    return DigestUtils.md5DigestAsHex(originStr.toString().getBytes());
  }

  @Override
  public boolean matches(CharSequence originStr, String expectStr) {
    return expectStr.equals(encode(originStr));
  }
}
