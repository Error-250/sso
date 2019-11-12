package com.sduwh.sso.service;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserGrant;

public interface UserService {
  User findOneUserByLogin(String login);

  void updateUserGrant(UserGrant userGrant);

  UserGrant findOneUserGrantByLogin(String login);
}
