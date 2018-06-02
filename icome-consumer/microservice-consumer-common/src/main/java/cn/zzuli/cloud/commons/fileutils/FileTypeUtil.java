package cn.zzuli.cloud.commons.fileutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;

/**
 * 判断文件类型(转化类型为message中类型)
 * 
 * @author panenming
 * @date 2016年2月14日
 * @version 1.0
 */
public class FileTypeUtil {
    public static int getMsgType(String contentType) {
        int rtn = 0;
        if (StringUtils.isBlank(contentType)) {
            return rtn;
        }
        if (contentType.contains("audio")) {
            rtn = 1;
        } else if (contentType.contains("video")) {
            rtn = 2;
        } else if (contentType.contains("html") || contentType.contains("htm")) {
            rtn = 3;
        } else if (contentType.contains("pdf")) {
            rtn = 4;
        } else if (contentType.contains("msword")
            || contentType
                    .contains("vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            rtn = 5;
        } else if (contentType.contains("vnd.ms-excel")
            || contentType
                    .contains("vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            rtn = 6;
        } else if (contentType.contains("vnd.ms-powerpoint")
            || contentType
                    .contains("vnd.openxmlformats-officedocument.presentationml.presentation")) {
            rtn = 7;
        } else if (contentType.contains("image")) {
            rtn = 8;
        } else if (contentType.contains("plain")) {
            rtn = 9;
        }
        return rtn;
    }

    public static String getMimeTypeOgl(Path path) {
        String mimeType = null;
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mimeType;
    }

    public static String getMimeTypeOglStr(String filePath) {
        Path path = Paths.get(filePath);
        String mimeType = null;
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == mimeType) {
        	Tika tika = new Tika();
        	File file = new File(filePath);
        	try {
				mimeType = tika.detect(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return mimeType;
    }

    public static String subFix(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }
}
