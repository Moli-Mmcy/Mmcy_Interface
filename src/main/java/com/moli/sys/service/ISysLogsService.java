package com.moli.sys.service;

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
public interface ISysLogsService {

    List<Map<String,Object>> listSlogs();

    Map<String,Object> getSlogsById(Integer id);
}
