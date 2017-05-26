package com.moli.common.controller;

import com.moli.utils.ResultCode;
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
 * @Date 2017/3/31
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/argumentsError")
    public Map argumentsError() {
        Map message = new HashMap<>();
        message.put("code", ResultCode.ERROR_ARGUMENTS_CODE);
        message.put("msg","参数错误:api_key|timestamp|sign不能为空");
        return message;
    }

    @RequestMapping("/timestampError")
    public Map timestampError() {
        Map message = new HashMap<>();
        message.put("code", ResultCode.ERROR_TIMESTAMP_CODE);
        message.put("msg","请求超时:链接时效已过期");
        return message;
    }

    @RequestMapping("/apiKeyError")
    public Map apiKeyError() {
        Map message = new HashMap<>();
        message.put("code", ResultCode.ERROR_API_KEY_CODE);
        message.put("msg","api_id值错误");
        return message;
    }

    @RequestMapping("/signError")
    public Map signError() {
        Map message = new HashMap<>();
        message.put("code", ResultCode.ERROR_SIGN_CODE);
        message.put("msg","签名错误");
        return message;
    }
}
