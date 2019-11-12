package com.sduwh.sso.mapper;

import com.sduwh.sso.domain.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClientMapper {
  @Select("select * from `client` where id = #{id}")
  Client selectOneClientById(@Param("id") String id);
}
