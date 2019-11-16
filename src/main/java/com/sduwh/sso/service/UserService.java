package com.sduwh.sso.service;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserAdditionalField;
import com.sduwh.sso.domain.UserGrant;

import java.util.List;

/** @author wxp */
public interface UserService {
  /**
   * 新增用户
   *
   * @param user 用户数据
   */
  void addUser(User user);

  /**
   * 更新用户数据
   *
   * @param user 待更新数据
   */
  void updateUser(User user);

  /**
   * 根据登录名查询用户
   *
   * @param login 登录名
   * @return 用户
   */
  User findOneUserByLogin(String login);

  /**
   * 根据用户id查询用户
   *
   * @param id 用户id
   * @return 用户
   */
  User findOneUserById(Long id);

  /**
   * 根据条件查询用户
   *
   * @param user 条件
   * @param limit limit
   * @param offset offset
   * @param additionalFields 额外附加数据
   * @return 一组用户
   */
  List<User> findUserByTemplate(
      User user, int limit, int offset, List<UserAdditionalField> additionalFields);

  /**
   * 更新用户授权
   *
   * @param userGrant 用户授权数据
   */
  void updateUserGrant(UserGrant userGrant);

  /**
   * 根据登录名查询用户授权信息
   *
   * @param login 登录名
   * @return 用户授权
   */
  UserGrant findOneUserGrantByLogin(String login);
}
