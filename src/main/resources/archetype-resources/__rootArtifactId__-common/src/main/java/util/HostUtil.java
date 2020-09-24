package com.jelly.boot.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 获取机器名称、机器IP
 *
 * @author zhangguodong
 */
public class HostUtil {

    private static String HOST_NAME = "UnknownHost";
    private static String HOST_ADDRESS = "UnknownHostAddress";

    static {
        HOST_NAME = initHostName();
        HOST_ADDRESS = initLocalHostAddress();
    }

    /**
     * 获取host 名称
     *
     * @return jelly-mac
     */
    public static String getHostName() {
        return HOST_NAME;
    }

    /**
     * 获取host地址
     *
     * @return 192.168.101.11
     */
    public static String getHostAddress() {
        return HOST_ADDRESS;
    }

    private static String initHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HOST_NAME;
    }

    private static String initLocalHostAddress() {
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    if (item.isLoopback() || !item.isUp() || item.isPointToPoint()
                            || address.getAddress() instanceof Inet6Address) {
                        continue;
                    }
                    return address.getAddress().getHostAddress();
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return HOST_ADDRESS;
    }

    /**
     * 是否符合本机优先， hostname 为空时返回 true，即默认当做本机处理
     */
    public static boolean matchLocalFirst(String hostname) {
        if (hostname == null || "".equals(hostname)) {
            return true;
        }
        return getHostName().equals(hostname);
    }
}