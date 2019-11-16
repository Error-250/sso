package com.sduwh.sso.mapper;

import com.sduwh.sso.domain.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/** @author wxp */
@Mapper
public interface ClientMapper {
  /**
   * 根据client id查询可授权的客户端
   *
   * @param id client id
   * @return client
   */
  @Select("select * from `client` where id = #{id}")
  Client selectOneClientById(@Param("id") String id);
}
