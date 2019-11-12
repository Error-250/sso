package com.sduwh.sso.grant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

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
