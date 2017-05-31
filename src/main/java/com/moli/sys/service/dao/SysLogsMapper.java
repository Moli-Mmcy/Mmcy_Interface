package com.moli.sys.service.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
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
public interface SysLogsMapper {

    @InsertProvider(type = SysLogsSqlProvider.class,method = "insert")
    int insert(Map<String,Object> paramsMap);

    @Select({
            "select * from sys_logs order by createTime desc"
    })
    List<Map<String,Object>> listSlogs();

    @Select({
            "select * from sys_logs where id = #{id}"
    })
    Map<String,Object> getSlogsById(@Param("id") Integer id);
}
