package com.moli.sys.dao;

import org.apache.ibatis.jdbc.SQL;

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
public class SysLogsSqlProvider {

    public String insert(Map<String,Object> paramsMap) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_logs");

        if (paramsMap.get("userId") != null) {
            sql.VALUES("userId", "#{userId,jdbcType=INTEGER}");
        }

        if (paramsMap.get("ip") != null) {
            sql.VALUES("ip", "#{ip,jdbcType=VARCHAR}");
        }

        if (paramsMap.get("url") != null) {
            sql.VALUES("url", "#{url,jdbcType=VARCHAR}");
        }

        if (paramsMap.get("method") != null) {
            sql.VALUES("method", "#{method,jdbcType=VARCHAR}");
        }

        if (paramsMap.get("clsNames") != null) {
            sql.VALUES("clsNames", "#{clsNames,jdbcType=VARCHAR}");
        }

        if (paramsMap.get("args") != null) {
            sql.VALUES("args", "#{args,jdbcType=VARCHAR}");
        }

        if (paramsMap.get("result") != null) {
            sql.VALUES("result", "#{result,jdbcType=VARCHAR}");
        }

        if (paramsMap.get("times") != null) {
            sql.VALUES("times", "#{times,jdbcType=INTEGER}");
        }

        sql.VALUES("createTime", "now()");

        return sql.toString();
    }
}
