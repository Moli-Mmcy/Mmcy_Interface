package com.moli.sys.service.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * <p>名称</p>
 * <p/>
 * <p>wikiURL</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2017/5/27
 */
public interface SysSqlLogsMapper {

    @Insert({
            "insert into sys_sqlLogs(sqltext,times,createTime)",
            "values",
            "(#{sqltext},#{times},now())"
    })
    int insert(@Param("sqltext") String sqltext, @Param("times") Long times);
}
