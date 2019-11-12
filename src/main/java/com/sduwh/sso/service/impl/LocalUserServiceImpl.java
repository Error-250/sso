package com.sduwh.sso.service.impl;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserGrant;
import com.sduwh.sso.mapper.UserMapper;
import com.sduwh.sso.service.UserService;

import javax.annotation.Resource;

public class LocalUserServiceImpl implements UserService {
  @Resource private UserMapper userMapper;

  @Override
  public User findOneUserByLogin(String login) {
    return userMapper.selectOneByLogin(login);
  }

  @Override
  public void updateUserGrant(UserGrant userGrant) {
    userMapper.updateUserGrant(userGrant);
  }

  @Override
  public UserGrant findOneUserGrantByLogin(String login) {
    return userMapper.findOneUserGrantByLogin(login);
  }
}
