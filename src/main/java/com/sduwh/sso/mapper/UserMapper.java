package com.sduwh.sso.mapper;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserGrant;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
  @Select("select * from `user` where login = #{login}")
  User selectOneByLogin(@Param("login") String login);

  @Insert("insert into `user`(`login`,`password`,`role`,`email`) values(#{login},#{password},#{role},#{email})")
  @Options(useGeneratedKeys = true)
  void insertUser(User user);

  @Update(
      "update `user_grant` set current_token = #{currentToken}"
          + " ,times = times + 1"
          + " where login = #{login}")
  void updateUserGrant(UserGrant grant);

  @Select("select * from user_grant where login = #{login}")
  UserGrant findOneUserGrantByLogin(@Param("login") String login);

  @Insert("insert into user_grant(`login`, `user_id`, `times`) values(#{login}, #{userId}, 1)")
  void insertUserGrant(UserGrant userGrant);
}
