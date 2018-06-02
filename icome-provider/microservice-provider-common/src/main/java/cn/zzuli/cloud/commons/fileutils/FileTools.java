package cn.zzuli.cloud.commons.fileutils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 文件操作工具类
 * 
 * @author panenming
 * @date 2016年2月14日
 * @version 1.0
 */
public class FileTools {
    /**
     * 修改为复用input，防止内存出现过多的占用
     */
    public static byte[] reducePhotoToByteArray(InputStream input, int width,
            int height) throws IOException {
        if (!input.markSupported()) {
            input = new BufferedInputStream(input);
        }
        // 为了能够复用整个流
        input.mark(input.available() + 1);
        BufferedImage read = ImageIO.read(input);
        int owidth = read.getWidth();
        int oheight = read.getHeight();
        double bi = ((double) owidth) / oheight;
        int nwidth, nheight;
        if (bi < (width / height)) {
            if (width > owidth) {
                nwidth = owidth;
                nheight = oheight;
            } else {
                nwidth = width;
                nheight = (int) (width * (1 / bi));
            }
        } else {
            if (height > oheight) {
                nwidth = owidth;
                nheight = oheight;
            } else {
                nheight = height;
                nwidth = (int) (height * bi);
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 复用 input
        input.reset();
        Thumbnails.of(input).size(nwidth, nheight).keepAspectRatio(true)
                .outputFormat("png").toOutputStream(out);
        return out.toByteArray();
    }

    public static byte[] reducePhotoToByteArray(BufferedImage read, int width,
            int height) throws IOException {
        if (read == null)
            throw new IOException("image not exists");
        int owidth = read.getWidth();
        int oheight = read.getHeight();
        double bi = ((double) owidth) / oheight;
        int nwidth, nheight;
        if (bi < (width / height)) {
            if (width > owidth) {
                nwidth = owidth;
                nheight = oheight;
            } else {
                nwidth = width;
                nheight = (int) (width * (1 / bi));
            }
        } else {
            if (height > oheight) {
                nwidth = owidth;
                nheight = oheight;
            } else {
                nheight = height;
                nwidth = (int) (height * bi);
            }
        }
        Image scaledInstance = read.getScaledInstance(nwidth, nheight,
                Image.SCALE_AREA_AVERAGING);
        BufferedImage tag = new BufferedImage(nwidth, nheight,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, nwidth, nheight);
        g.drawImage(scaledInstance, 0, 0, null);
        return toByteArray(tag);
    }

    private static byte[] toByteArray(BufferedImage read) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(read, "JPEG", output);
        return output.toByteArray();
    }

    public static void writeFileToResponse(FileCacheData fileCacheData,
            HttpServletResponse response) throws Exception {
        if (fileCacheData != null)
            writeFileToResponse(fileCacheData.getFilename(),
                    fileCacheData.getContentType(), fileCacheData.getData(),
                    response);
    }

    public static void writePhotoToResponse(FileCacheData fileCacheData,
            HttpServletResponse response, String key) throws Exception {
        if (fileCacheData != null)
            writePhoto(fileCacheData.getFilename(),
                    fileCacheData.getContentType(), fileCacheData.getData(),
                    response, key);
    }

    public static void writeAttToResponse(FileCacheData fileCacheData,
            HttpServletResponse response) throws Exception {
        if (fileCacheData != null)
            writeFileToResponse(fileCacheData.getFilename(),
                    fileCacheData.getContentType(), fileCacheData.getData(),
                    response);
    }

    public static void writeFileToResponse(String fileName, String contentType,
            byte[] data, HttpServletResponse response) throws Exception {
        response.setContentType(contentType);
        response.setContentLength(data.length);
        boolean isImage = false;
        if (contentType != null) {
            isImage = contentType.contains("image")
                || contentType.contains("audio")
                || contentType.contains("video");
        }
        if (isImage) {
            response.setHeader("Cache-Control", "max-age=315360");
        } else {
            if(!StringUtils.isBlank(fileName)){
                byte b[] = fileName.getBytes("gbk");
                String fn = new String(b, "ISO8859_1");
                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + fn + "\"");
            }
        }
        response.getOutputStream().write(data);
    }

    public static void writePhoto(String fileName, String contentType,
            byte[] data, HttpServletResponse response, String key)
            throws Exception {
        response.setContentType(contentType);
        response.setContentLength(data.length);
        if (!StringUtils.isBlank(key)) {
            response.setHeader("ETag", key);
        } else {
            response.setHeader("Cache-Control", "max-age=315360");
        }
        response.getOutputStream().write(data);
    }

    public static void writeAttToResponse(String fileName, String contentType,
            byte[] data, HttpServletResponse response) throws Exception {
        if(!StringUtils.isBlank(contentType)){
            if (!contentType.contains("html")) {
                if(!StringUtils.isBlank(fileName)){
                    byte b[] = fileName.getBytes("gbk");
                    String fn = new String(b, "ISO8859_1");
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + fn + "\"");
                }
            }
        }else{
            if(!StringUtils.isBlank(fileName)){
                byte b[] = fileName.getBytes("gbk");
                String fn = new String(b, "ISO8859_1");
                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + fn + "\"");
            }
        }
        response.setContentType(contentType);
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
    }

    public static void writeAttFileToResponse(String fileName,
            String contentType, byte[] data, HttpServletResponse response)
            throws Exception {
        response.setContentType(contentType);
        response.setContentLength(data.length);
        if(!StringUtils.isBlank(fileName)){
            byte b[] = fileName.getBytes("gbk");
            String fn = new String(b, "ISO8859_1");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + fn + "\"");
        }
        response.getOutputStream().write(data);
    }

    public static void writeResumeFileToResponse(FileCacheData fileData,
            String range, HttpServletResponse response) throws Exception {
        byte[] data = fileData.getData();
        InputStream in = null;
        ServletOutputStream out = null;
        // set http header
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-download");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(data.length));
        response.setContentLength(data.length);
        byte b[] = fileData.getFilename().getBytes("gbk");
        String fn = new String(b, "ISO8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=\""
            + fn + "\"");
        long pos = 0;
        if (null != range) {
            // 断点续传
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            try {
                pos = Long.parseLong(range.replaceAll("bytes=", "").replaceAll(
                        "-", ""));
            } catch (NumberFormatException e) {
                pos = 0;
            }
        }
        try {
            out = response.getOutputStream();
            in = new ByteArrayInputStream(data);
            String contentRange = new StringBuffer("bytes ").append(pos + "")
                    .append("-").append((data.length - 1) + "").append("/")
                    .append(data.length + "").toString();
            response.setHeader("Content-Range", contentRange);
            in.skip(pos);
            byte[] buffer = new byte[1024 * 10];
            int length = 0;
            while ((length = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, length);
            }
        } finally {
            try {
                if (null != out)
                    out.flush();
                if (null != out)
                    out.close();
                if (null != in)
                    in.close();
            } catch (IOException e) {
            }
        }
    }
    /*
     * public static void main(String[] args) throws IOException { InputStream
     * in = new FileInputStream(new File(
     * "C:/Users/panenming.LXAJTX4579/Desktop/icoach@3x.png")); byte[] data =
     * reducePhotoToByteArray(in, 91, 91); try { FileImageOutputStream
     * imageOutput = new FileImageOutputStream( new File(
     * "C:/Users/panenming.LXAJTX4579/Desktop/testsmall.jpg"));
     * imageOutput.write(data, 0, data.length); imageOutput.close(); } catch
     * (Exception ex) { System.out.println("Exception: " + ex);
     * ex.printStackTrace(); } }
     */
}
