package com.sduwh.sso.mapper.handler;

import com.sduwh.sso.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.assertj.core.util.Lists;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据库类型转换 List<User.Role> to String
 *
 * @author wxp
 */
public class UserRoleHandler implements TypeHandler<List<User.Role>> {
  private String delimiter = ",";

  @Override
  public void setParameter(
      PreparedStatement preparedStatement, int i, List<User.Role> roles, JdbcType jdbcType)
      throws SQLException {
    preparedStatement.setString(
        i, roles.stream().map(User.Role::name).collect(Collectors.joining(delimiter)));
  }

  @Override
  public List<User.Role> getResult(ResultSet resultSet, String s) throws SQLException {
    String roles = resultSet.getString(s);
    if (StringUtils.isEmpty(roles)) {
      return null;
    }
    return Lists.newArrayList(roles.split(delimiter)).stream()
        .map(User.Role::valueOf)
        .collect(Collectors.toList());
  }

  @Override
  public List<User.Role> getResult(ResultSet resultSet, int i) throws SQLException {
    String roles = resultSet.getString(i);
    if (StringUtils.isEmpty(roles)) {
      return null;
    }
    return Lists.newArrayList(roles.split(delimiter)).stream()
        .map(User.Role::valueOf)
        .collect(Collectors.toList());
  }

  @Override
  public List<User.Role> getResult(CallableStatement callableStatement, int i) throws SQLException {
    String roles = callableStatement.getString(i);
    if (StringUtils.isEmpty(roles)) {
      return null;
    }
    return Lists.newArrayList(roles.split(delimiter)).stream()
        .map(User.Role::valueOf)
        .collect(Collectors.toList());
  }
}
