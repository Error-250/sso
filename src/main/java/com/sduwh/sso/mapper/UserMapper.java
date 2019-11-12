package com.sduwh.sso.mapper;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserGrant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
  @Select("select * from `user` where login = #{login}")
  User selectOneByLogin(@Param("login") String login);

  @Update(
      "update `user_grant` set current_token = #{currentToken}"
          + " ,times = times + 1"
          + " where login = #{login}")
  void updateUserGrant(UserGrant grant);

  @Select("select * from user_grant where login = #{login}")
  UserGrant findOneUserGrantByLogin(@Param("login") String login);
}
