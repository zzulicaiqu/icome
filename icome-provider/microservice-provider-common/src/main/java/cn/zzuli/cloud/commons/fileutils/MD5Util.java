package cn.zzuli.cloud.commons.fileutils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.alibaba.fastjson.util.IOUtils;

public class MD5Util {
    static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    private static ThreadLocal<MessageDigest> messageDigestHolder = new ThreadLocal<MessageDigest>();

    static {
        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            messageDigestHolder.set(message);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMD5Format(String data) {
        if (data == null)
            data = "";
        try {
            MessageDigest message = messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }

            message.update(data.getBytes("utf-8"));

            return digestHex(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMD5Format(byte[] data) {
        try {
            MessageDigest message = messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }

            message.update(data);

            return digestHex(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMD5Format(File file) {
        if (file == null)
            return "";
        FileInputStream in = null;
        try {
            MessageDigest message = messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            message.update(byteBuffer);

            return digestHex(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(in);
        }
    }

    private static String digestHex(MessageDigest message) {
        byte[] b = message.digest();

        String digestHexStr = "";
        for (int i = 0; i < 16; ++i) {
            digestHexStr = digestHexStr + byteHEX(b[i]);
        }

        return digestHexStr;
    }

    private static String byteHEX(byte ib) {
        char[] ob = new char[2];
        ob[0] = hexDigits[(ib >>> 4 & 0xF)];
        ob[1] = hexDigits[(ib & 0xF)];

        return new String(ob);
    }
}
