package com.sduwh.sso.service.impl;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserGrant;
import com.sduwh.sso.mapper.UserMapper;
import com.sduwh.sso.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public class LocalUserServiceImpl implements UserService, UserDetailsService {
  @Resource private UserMapper userMapper;

  @Override
  public User findOneUserByLogin(String login) {
    return userMapper.selectOneByLogin(login);
  }

  @Override
  @Transactional(rollbackFor=Exception.class)
  public void addUser(User user) {
    userMapper.insertUser(user);
    UserGrant userGrant = new UserGrant();
    userGrant.setLogin(user.getLogin());
    userGrant.setUserId(user.getId());
    userMapper.insertUserGrant(userGrant);
  }

  @Override
  public void updateUserGrant(UserGrant userGrant) {
    userMapper.updateUserGrant(userGrant);
  }

  @Override
  public UserGrant findOneUserGrantByLogin(String login) {
    return userMapper.findOneUserGrantByLogin(login);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return findOneUserByLogin(username);
  }
}
