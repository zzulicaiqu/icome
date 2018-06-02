package cn.zzuli.cloud.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class OsUtils {
    private static final Logger logger = LoggerFactory.getLogger(OsUtils.class);

    public static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.warn("获取服务器地址失败！", e);
            return null;
        }
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.warn("获取服务器名称失败！", e);
            return null;
        }
    }

    public static void main(String[] args) {
        logger.debug(OsUtils.getHostName());
        logger.debug(OsUtils.getHostAddress());
    }
}
