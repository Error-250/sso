package com.sduwh.sso.mapper;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserGrant;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** @author wxp */
@Mapper
public interface UserMapper {
  /**
   * 新增用户
   *
   * @param user 用户数据
   */
  @Insert(
      "insert into `user`(`login`,`password`,`role`,`email`) values(#{login},#{password},#{role},#{email})")
  @Options(useGeneratedKeys = true)
  void insertUser(User user);

  /**
   * 更新用户
   *
   * @param user 待更新的用户数据, 必传id, login
   */
  @Update(
      "<script>"
          + "update `user` set login = #{login}"
          + "<if test='password != null'> ,password=#{password}</if>"
          + "<if test='role != null'>,role=#{role}</if>"
          + "<if test='email != null'>,email=#{email}</if>"
          + " where id = #{id}"
          + "</script>")
  void updateUser(User user);

  /**
   * 根据登录名查询用户
   *
   * @param login 登录名
   * @return 用户
   */
  @Select("select * from `user` where login = #{login}")
  User selectOneUserByLogin(@Param("login") String login);

  /**
   * 根据用户id查询用户
   *
   * @param userId 用户id
   * @return 用户
   */
  @Select("select * from `user` where id = #{userId}")
  User selectOneUserById(@Param("userId") Long userId);

  /**
   * 根据条件查询一批用户, 支持分页
   *
   * @param user 用于筛选的数据
   * @param limit limit
   * @param offset offset
   * @return 一组用户
   */
  @Select(
      "<script>"
          + "select * from user where 1 = 1"
          + "<if test='user.login != null'> and login = #{user.login}</if>"
          + "<if test='user.role != null'> and role = #{user.role}</if>"
          + " limit #{limit} offset #{offset}"
          + "</script>")
  List<User> selectUsers(
      @Param("user") User user, @Param("limit") int limit, @Param("offset") int offset);

  /**
   * 新增用户授权数据, 登录次默认为0
   *
   * @param userGrant 用户授权数据
   */
  @Insert("insert into user_grant(`login`, `user_id`, `times`) values(#{login}, #{userId}, 0)")
  void insertUserGrant(UserGrant userGrant);

  /**
   * 更新用户授权数据, 登录次数加1
   *
   * @param grant 待更新的用户授权数据
   */
  @Update(
      "update `user_grant` set current_token = #{currentToken}"
          + " ,times = times + 1"
          + " where login = #{login}")
  void updateUserGrant(UserGrant grant);

  /**
   * 根据用户id查询用户授权
   *
   * @param userId 用户id
   * @return 用户授权
   */
  @Select("select * from user_grant where user_id = #{userId}")
  UserGrant selectUserLastGrant(@Param("userId") Long userId);

  /**
   * 根据登录名查询用户授权数据
   *
   * @param login 登录名
   * @return 用户授权
   */
  @Select("select * from user_grant where login = #{login}")
  UserGrant findOneUserGrantByLogin(@Param("login") String login);
}
