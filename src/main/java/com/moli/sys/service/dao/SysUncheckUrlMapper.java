package com.moli.sys.service.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>名称</p>
 * <p/>
 * <p>wikiURL</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2017/5/26
 */
public interface SysUncheckUrlMapper {

    @Select({
            "select count(*) from sys_uncheck_url",
            "where url = #{url}"
    })
    int countByUrl(@Param("url") String url);
}
