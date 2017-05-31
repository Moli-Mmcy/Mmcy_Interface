package com.moli.sys.service.impl;

import com.moli.sys.service.dao.SysLogsMapper;
import com.moli.sys.service.ISysLogsService;
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

    @Cacheable({"listSlogs"})
    @Override
    public List<Map<String, Object>> listSlogs()
    {
        return sysLogsMapper.listSlogs();
    }
}
