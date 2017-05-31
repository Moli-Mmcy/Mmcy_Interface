package com.moli.sys.service.impl;

import com.moli.sys.service.ISysLogsService;
import com.moli.sys.service.dao.SysLogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 * <p/>
 * <p>wikiURL</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2017/5/27
 */
@Service("sysLogsService")
public class SysLogsServiceImpl implements ISysLogsService{

    @Autowired
    private SysLogsMapper sysLogsMapper;

    @Override
    @Cacheable(value = "myCache",key = "'listSlogs'")
    public List<Map<String, Object>> listSlogs()
    {
        return sysLogsMapper.listSlogs();
    }

    @Override
    @Cacheable(value = "myCache",key = "'getSlogsById'")
    public Map<String, Object> getSlogsById(Integer id)
    {
        System.out.println("查询数据库拉:" + id);
        return sysLogsMapper.getSlogsById(id);
    }
}
