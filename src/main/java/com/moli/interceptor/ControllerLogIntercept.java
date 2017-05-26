package com.moli.interceptor;

import com.moli.sys.dao.SysApiKeyMapper;
import com.moli.sys.dao.SysUncheckUrlMapper;
import com.moli.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Enumeration;
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
public class ControllerLogIntercept extends HandlerInterceptorAdapter {

    private static final long invalidTime = 5 * 60 * 1000;//时效五分钟
    private static final String ERROR_argumentsError_URL = "/moLi/common/argumentsError";
    private static final String ERROR_TIMESTAMP_URL = "/moLi/common/timestampError";
    private static final String ERROR_API_KEY_URL = "/moLi/common/apiKeyError";
    private static final String ERROR_SIGN_URL = "/moLi/common/signError";
    private static Logger logger = Logger.getLogger( ControllerLogIntercept.class );

    @Autowired
    private SysUncheckUrlMapper sysUncheckUrlMapper;

    @Autowired
    private SysApiKeyMapper sysApiKeyMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        boolean success = super.preHandle(request, response, handler);
        String requestURL = request.getRequestURI();
        String ipAddress = this.getIpAddress(request);
        logger.debug( "用户IP地址为["+ ipAddress +"]，访问了[" + requestURL + "]" );
        for( Enumeration parameterNames = request.getParameterNames(); parameterNames.hasMoreElements(); )
        {
            String paramName = ( String ) parameterNames.nextElement();
            logger.debug( "参数名[" + paramName + "]参数值[" + request.getParameter( paramName ) + "]" );
        }
        //判断请求是否无需校验
        if (!checkUrl(requestURL))
        {
            String api_id = request.getParameter("api_id");
            String timestamp = request.getParameter("timestamp");
            String sign = request.getParameter("sign");
            //验证参数是否缺失
            if (StringUtils.isBlank(api_id) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(sign))
            {
                response.sendRedirect(ControllerLogIntercept.ERROR_argumentsError_URL);
                return false;
            }
            //验证请求是否超时
            long serverTime = Calendar.getInstance().getTimeInMillis();
            long clientTime = Long.parseLong(timestamp);
            long flag = serverTime - clientTime;
            if (flag < 0 || flag > ControllerLogIntercept.invalidTime)
            {
                response.sendRedirect(ControllerLogIntercept.ERROR_TIMESTAMP_URL);
                return false;
            }
            //校验api_key是否正确
            Map<String,Object> apiMap = sysApiKeyMapper.getInfoByApiId(api_id);
            if(apiMap == null || apiMap.isEmpty())
            {
                response.sendRedirect(ControllerLogIntercept.ERROR_API_KEY_URL);
                return false;
            }
            String api_key = (String) apiMap.get("api_key");
            //校验签名
            //本地根据md5加密生成签名做对比
            String localSign = Md5Utils.EncoderByMd5(requestURL + api_id + api_key + timestamp);
            if(!sign.equals(localSign))
            {
                response.sendRedirect(ControllerLogIntercept.ERROR_SIGN_URL);
                return false;
            }
        }

        return success;
    }

    boolean checkUrl(String requestURL)
    {
        int count = sysUncheckUrlMapper.countByUrl(requestURL);
        return count > 0;
    }

    /**
     * 从HttpServletRequest获取用户客户端IP地址
     */
    private String getIpAddress( HttpServletRequest request )
    {
        String ip = request.getHeader( "X-Forwarded-For" );

        if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getHeader( "Proxy-Client-IP" );
            }
            if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getHeader( "WL-Proxy-Client-IP" );
            }
            if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getHeader( "HTTP_CLIENT_IP" );
            }
            if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
            }
            if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getRemoteAddr();
            }
        }
        else if( ip.length() > 15 )
        {
            String[] ips = ip.split( "," );
            for( String ip1 : ips )
            {
                if( !( "unknown".equalsIgnoreCase( ip1 ) ) )
                {
                    ip = ip1;
                    break;
                }
            }
        }
        return ip;
    }
}
