package com.moli.aop;

import com.moli.sys.dao.SysLogsMapper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
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
@Component
@Aspect
public class InterfaceLogAop {

    private static Logger logger = Logger.getLogger(InterfaceLogAop.class);

    @Autowired
    private SysLogsMapper sysLogsMapper;

    //定义切点
    @Pointcut("execution(public * com.moli.*.controller.*.*(..))")
    public void logPoint()
    {

    }

    @Around("logPoint()")
    public Object saveLogs(ProceedingJoinPoint joinPoint)
    {
        long startTime = System.currentTimeMillis();
        //调用方法
        String methodClass = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获取request信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = "", httpMethod = "", ip = "", args = "";
        if (attributes != null)
        {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            url = request.getRequestURL().toString();
            httpMethod = request.getMethod();
            ip = getIpAddress(request);
            for (Enumeration parameterNames = request.getParameterNames(); parameterNames.hasMoreElements(); )
            {
                String paramName = (String) parameterNames.nextElement();
                args += "参数名[" + paramName + "]参数值[" + request.getParameter(paramName) + "]";
                logger.debug("参数名[" + paramName + "]参数值[" + request.getParameter(paramName) + "]");
            }
        }
        long methodEnd;
        Object result = null;
        //执行方法
        try
        {
            result = joinPoint.proceed();
        } catch (Throwable throwable)
        {
            methodEnd = System.currentTimeMillis();// 记录出现异常时的执行时间
            throwable.printStackTrace();
            result = throwable.getMessage();
            String msg = "用户IP:" + ip + ",地址" + url + ",\n请求方式:" + httpMethod + ",方法:" + methodClass +
                    ",参数:" + args + ",结果:" + result + ",执行时间:" + (methodEnd - startTime);
            logger.error(msg);
            Map<String,Object> paramsMap = new HashMap<>();
            paramsMap.put("ip", ip);
            paramsMap.put("url", url);
            paramsMap.put("method", httpMethod);
            paramsMap.put("clsNames", methodClass);
            paramsMap.put("args", args);
            paramsMap.put("result", JSONObject.fromObject(result).toString());
            paramsMap.put("times", methodEnd - startTime);
            sysLogsMapper.insert(paramsMap);
        }
        methodEnd = System.currentTimeMillis();
        String msg = "用户IP:" + ip + ",地址" + url + ",\n请求方式:" + httpMethod + ",方法:" + methodClass +
                ",参数:" + args + ",结果:" + result + ",执行时间:" + (methodEnd - startTime);
        logger.info(msg);
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("ip", ip);
        paramsMap.put("url", url);
        paramsMap.put("method", httpMethod);
        paramsMap.put("clsNames", methodClass);
        paramsMap.put("args", args);
        paramsMap.put("result", JSONObject.fromObject(result).toString());
        paramsMap.put("times", methodEnd - startTime);
        sysLogsMapper.insert(paramsMap);
        return result;
    }

    /**
     * 从HttpServletRequest获取用户客户端IP地址
     */
    private String getIpAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15)
        {
            String[] ips = ip.split(",");
            for (String ip1 : ips)
            {
                if (!("unknown".equalsIgnoreCase(ip1)))
                {
                    ip = ip1;
                    break;
                }
            }
        }
        return ip;
    }
}
