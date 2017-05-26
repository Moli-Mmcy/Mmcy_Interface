package com.moli.sys.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>名称</p>
 * <p/>
 * <p>wikiURL</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2017/5/26
 */
public interface SysApiKeyMapper {

    @Select({
            "select * from sys_apikey",
            "where api_id = #{api_id}"
    })
    Map<String,Object> getInfoByApiId(@Param("api_id") String api_id);
}
