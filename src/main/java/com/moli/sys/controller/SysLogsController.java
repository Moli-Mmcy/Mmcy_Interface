package com.moli.sys.controller;

import com.moli.sys.service.ISysLogsService;
import com.moli.utils.ResultCode;
import net.sf.ehcache.CacheManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
@RestController
@RequestMapping("/sys/logs")
public class SysLogsController {

    private static Logger logger = Logger.getLogger(SysLogsController.class);

    @Autowired
    private ISysLogsService sysLogsService;

    @RequestMapping("/list")
    public Map list() {
        Map<String,Object> dataMap = new HashMap<>();
        Map message = new HashMap<>();
        message.put("error_code", ResultCode.SUCCESS_CODE);
        dataMap.put("listSlogs", sysLogsService.listSlogs());
        message.put("data", dataMap);
        return message;
    }

    @RequestMapping("/id")
    public Map getSlogsById(Integer id) {
        Map<String,Object> dataMap = new HashMap<>();
        Map message = new HashMap<>();
        message.put("error_code", ResultCode.SUCCESS_CODE);
        dataMap.put("listSlogs", sysLogsService.getSlogsById(id));
        message.put("data", dataMap);
        return message;
    }
}
