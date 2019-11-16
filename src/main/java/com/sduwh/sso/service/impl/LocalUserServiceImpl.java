package com.sduwh.sso.service.impl;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserAdditionalField;
import com.sduwh.sso.domain.UserGrant;
import com.sduwh.sso.mapper.UserMapper;
import com.sduwh.sso.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author wxp
 */
public class LocalUserServiceImpl implements UserService, UserDetailsService {
  @Resource private UserMapper userMapper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addUser(User user) {
    userMapper.insertUser(user);
    UserGrant userGrant = new UserGrant();
    userGrant.setLogin(user.getLogin());
    userGrant.setUserId(user.getId());
    userMapper.insertUserGrant(userGrant);
  }

  @Override
  public void updateUser(User user) {
    userMapper.updateUser(user);
  }

  @Override
  public User findOneUserByLogin(String login) {
    return userMapper.selectOneUserByLogin(login);
  }

  @Override
  public User findOneUserById(Long id) {
    return userMapper.selectOneUserById(id);
  }

  @Override
  public List<User> findUserByTemplate(
      User user, int limit, int offset, List<UserAdditionalField> additionalFields) {
    List<User> users = userMapper.selectUsers(user, limit, offset);
    if (additionalFields != null && additionalFields.size() > 0) {
      for (UserAdditionalField userAdditionalField : additionalFields) {
        switch (userAdditionalField) {
          case LAST_GRANT:
            users.forEach(
                user1 ->
                    user1.setLastGrant(
                        Collections.singletonList(userMapper.selectUserLastGrant(user1.getId()))));
          default:
        }
      }
    }
    return users;
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
