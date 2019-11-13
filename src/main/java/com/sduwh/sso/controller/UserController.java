package com.sduwh.sso.controller;

import com.sduwh.sso.domain.Client;
import com.sduwh.sso.domain.User;
import com.sduwh.sso.grant.SsoGranter;
import com.sduwh.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
  @Resource private UserService userService;
  @Resource private PasswordEncoder passwordEncoder;
  @Resource private OAuth2RequestFactory oAuth2RequestFactory;
  @Resource private ClientDetailsService clientDetailsService;
  @Resource private SsoGranter ssoGranter;

  @RequestMapping(value = "/logon", method = RequestMethod.POST)
  public ResponseEntity<OAuth2AccessToken> logon(
      @RequestParam("login") String login,
      @RequestParam("password") String password,
      @RequestParam("confirm_password") String confirmPassword,
      @RequestParam("role") User.Role role,
      @RequestParam("client_id") Client.ClientIdE clientIdE,
      @RequestParam(value = "email", required = false) String email) {
    boolean isParamValid =
        !StringUtils.isEmpty(login)
            && !StringUtils.isEmpty(password)
            && !StringUtils.isEmpty(confirmPassword)
            && password.equals(confirmPassword)
            && (User.Role.STUDENT.equals(role) || User.Role.TEACHER.equals(role));
    if (!isParamValid) {
      throw new InvalidParameterException();
    }
    if (User.Role.STUDENT.equals(role)) {
      int currentYear = LocalDate.now().getYear();
      boolean isLoginValid =
          login.length() == 12 && Integer.parseInt(login.substring(0, 4)) >= currentYear - 6;
      if (!isLoginValid) {
        throw new InvalidParameterException();
      }
    }
    Client client = (Client) clientDetailsService.loadClientByClientId(clientIdE.name());
    if (client == null) {
      throw new InvalidClientException(clientIdE.name());
    }
    User user = userService.findOneUserByLogin(login);
    if (user != null) {
      throw new InvalidGrantException("User is existed");
    }
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setEmail(email);
    newUser.setRole(role);
    userService.addUser(newUser);
    // 获取token
    Map<String, String> param = new HashMap<>(2);
    param.put("username", login);
    param.put("password", password);
    TokenRequest request = oAuth2RequestFactory.createTokenRequest(param, client);
    return ResponseEntity.ok(ssoGranter.grant(client.getGrantType().toLowerCase(), request));
  }
}
