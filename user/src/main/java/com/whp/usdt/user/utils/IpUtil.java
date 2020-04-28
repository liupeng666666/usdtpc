
package com.whp.usdt.user.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 张吉伟
 * @data : 2018/7/11 20:55
 * @descrpition :
 */
public class IpUtil {

    public static String ip(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");
        System.out.println("remoteAddr:" + remoteAddr);
        System.out.println("forwarded:" + forwarded);
        System.out.println("realIp:" + realIp);
        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }
}
