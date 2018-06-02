package cn.zzuli.cloud.commons.fileutils;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;

/**
 * @author panenming
 * @date 2016年2月14日
 * @version 1.0
 */
public class FileCacheData implements Serializable {
    private static final long serialVersionUID = 5526273869862591800L;
    private String            contentType;
    private String            filename;
    private byte[]            data;

    public FileCacheData(InputStream inputStream) throws Exception {
        this.data = IOUtils.toByteArray(inputStream);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
