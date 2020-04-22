package com.fattyca1.common.util.web;

import com.fattyca1.common.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>request util</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/2/17
 * @since 1.0
 */
public class RequestUtils {

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isRemoteAddressNotFound(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        }
        if (isRemoteAddressNotFound(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        if (isRemoteAddressNotFound(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isRemoteAddressNotFound(remoteAddr)) {
            remoteAddr = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isRemoteAddressNotFound(remoteAddr)) {
            remoteAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isRemoteAddressNotFound(remoteAddr)) {
            String host = request.getHeader("Host");
            if ("0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()) || StringUtils.contains(host, "localhost") || StringUtils.contains(host,
                    "127.0.0.1")) {
                //获取本地IP
                remoteAddr = getLocalAddress();
            }
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }


    private static boolean isRemoteAddressNotFound(String remoteAddr) {
        return StringUtils.isEmpty(remoteAddr) || "unknown".equalsIgnoreCase(remoteAddr);
    }


    public static String getLocalAddress() {
        String la = "127.0.0.1";
        try {
            InetAddress localAddr = InetAddress.getLocalHost();
            if (!localAddr.isLoopbackAddress() && !localAddr.isLinkLocalAddress() && localAddr.isSiteLocalAddress()) {
                la = localAddr.getHostAddress();
                return la;
            }
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            NETWORK_LOOP:
            for (; n.hasMoreElements(); ) {
                NetworkInterface e = n.nextElement();

                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements(); ) {
                    InetAddress addr = a.nextElement();
                    if (!addr.isLoopbackAddress() && !addr.isLinkLocalAddress() && addr.isSiteLocalAddress()) {
                        la = addr.getHostAddress();
                        break NETWORK_LOOP;
                    }
                }
            }
        } catch (Exception ignored) {

        }
        return la;
    }

    public static Map<String, String> obtainRequestHeadInfo(HttpServletRequest request) {
        Map<String, String> ret = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            ret.put(key, value);
        }
        return ret;
    }
}
