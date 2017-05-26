package com.moli.sys.dao;

import org.apache.ibatis.annotations.InsertProvider;

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
}
