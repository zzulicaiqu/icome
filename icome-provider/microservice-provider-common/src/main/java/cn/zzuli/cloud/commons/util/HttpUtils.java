package cn.zzuli.cloud.commons.util;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import cn.zzuli.cloud.commons.exception.EnnHttpException;

/**
 * Http工具类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class HttpUtils {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final int TIMEOUT = 10 * 1000;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String USER_AGENT = "User-Agent";
    public static final String CONNECTION = "Connection";
    public static final String KEEP_ALIVE = "keep-alive";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String ZH_CN_ZH_Q_0_8 = "zh-CN,zh;q=0.8";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String UTF_8 = "utf-8";
    public static final String ACCEPT_ENCODING1 = "Accept-Encoding";
    public static final String GZIP = "gzip";

    private static volatile CloseableHttpClient httpClient;

    private static HttpUtils instance;

    private HttpUtils() {
        init();
    }

    public synchronized static HttpUtils getInstance() {
        if (instance == null) {
            instance = new HttpUtils();
        }
        return instance;
    }

    private void init() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new TrustStrategy() {
                        @Override
                        public boolean isTrusted(final X509Certificate[] chain, final String authType)
                                throws CertificateException {
                            return true;
                        }
                    }).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            logger.error("Http client initial failure!", e);
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        // Create a registry of custom connection socket factories for supported protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslsf).build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(100);

        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();
        // Configure the connection manager to use connection configuration
        // either
        // by default or for a specific host.
        connManager.setDefaultConnectionConfig(connectionConfig);

        // Create global request configuration
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setSocketTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .addInterceptorFirst(new HttpRequestInterceptor() {
                    @Override
                    public void process(final HttpRequest request, final HttpContext context)
                            throws HttpException, IOException {
                        if (!request.containsHeader(ACCEPT_ENCODING)) {
                            request.addHeader("Accept-Encoding", "gzip");
                        }
                        /*if (!request.containsHeader(CONTENT_TYPE)) {
                            request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                        }*/
                        if (!request.containsHeader(USER_AGENT)) {
                            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; rv:45.0) Gecko/20100101 Firefox/45.0");
                        }
                        request.addHeader(CONNECTION, KEEP_ALIVE);
                        request.addHeader(ACCEPT_LANGUAGE, ZH_CN_ZH_Q_0_8);
                        request.addHeader(ACCEPT_CHARSET, UTF_8);
                        request.addHeader(ACCEPT_ENCODING1, GZIP);
                    }
                }).addInterceptorFirst(new HttpResponseInterceptor() {
                    @Override
                    public void process(final HttpResponse response, final HttpContext context)
                            throws HttpException, IOException {
                        org.apache.http.HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            Header ceHeader = entity.getContentEncoding();
                            if (ceHeader != null) {
                                HeaderElement[] elements = ceHeader.getElements();
                                for (HeaderElement element : elements) {
                                    if ("gzip".equalsIgnoreCase(element.getName())) {
                                        response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }).build();
    }

    /**
     * 发送给请求
     *
     * @param url
     * @return
     */
    public String get(final String url) throws IOException {
        return request(new HttpGet(url));
    }

    public String post(final String url, final String param) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (param != null) {
            httpPost.setEntity(new StringEntity(param, "utf-8"));
        }
        return request(httpPost);
    }

    public String postJson(final String url, final String param) throws IOException {
        long startTime = System.currentTimeMillis();
        HttpPost httpPost = new HttpPost(url);
        if (param != null) {
            httpPost.setEntity(new StringEntity(param, "utf-8"));
            httpPost.setHeader("Content-type", "application/json");
        }
        String result = request(httpPost);
        if (logger.isDebugEnabled()) {
            logger.debug("postJson耗时{},url={},param={}", System.currentTimeMillis() - startTime, url, param);
        }
        return result;
    }
    
    public String postJsonWithToken(final String url, final String param,String token) throws IOException {
        long startTime = System.currentTimeMillis();
        HttpPost httpPost = new HttpPost(url);
        if (param != null) {
            httpPost.setEntity(new StringEntity(param, "utf-8"));
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("token", token);
        }
        String result = request(httpPost);
        if (logger.isDebugEnabled()) {
            logger.debug("postJson耗时{},url={},param={}", System.currentTimeMillis() - startTime, url, param);
        }
        return result;
    }

    public String postFileWithToken(final String url, MultipartFile file, String ownerId, String fileName,String token) {
    	String result = null ; 
    	if (file.isEmpty()) {
            return result;
         }
    	long startTime = System.currentTimeMillis();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        try {
			builder.addBinaryBody("file", file.getBytes(),
			        ContentType.DEFAULT_BINARY, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}// 文件流
        builder.addTextBody("fileName", fileName);// 类似浏览器表单提交，对应input的name和value
        builder.addTextBody("ownerId", ownerId);
        HttpEntity entity = builder.build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("token", token);
		try {
			 CloseableHttpResponse resp = httpClient.execute(httpPost);
		        try {
		            int statusCode = resp.getStatusLine().getStatusCode();
		            HttpEntity resEntity = resp.getEntity();
		            if (statusCode == HttpStatus.SC_OK) {
		                String rtn = EntityUtils.toString(resEntity);
		                return rtn;
		            }
		            EntityUtils.consume(resEntity);
		        } finally {
		            if (null != resp) {
		                resp.close();
		            }
		        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        if (logger.isDebugEnabled()) {
            logger.debug("postJson耗时{},url={},param={}", System.currentTimeMillis() - startTime, url, fileName);
        }
        return result;
    }
    
    public String request(final HttpRequestBase httpRequest) throws IOException {
        String result;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpRequest);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            int a = 100;
            int b = 2;
            if (statusCode / a == b) {
                if (logger.isTraceEnabled()) {
                    logger.trace("http:url={}, status={}", httpRequest.getURI().toString(),
                            statusLine.getStatusCode() + "," + statusLine.getReasonPhrase());
                }
            } else {
                logger.warn("接口调用失败！url:{}, StatusCode:{}, response:{}",
                        httpRequest.getURI().toString(), statusLine.getStatusCode(), result);
                throw new EnnHttpException("接口调用失败！url:" + httpRequest.getURI().toString()
                        + ", StatusCode:" + statusLine.getStatusCode());
            }
            EntityUtils.consume(httpEntity);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception ex) {
                logger.warn("Http response close failure！", ex);
            }
        }
        return result;
    }

    /*
    private class AllTrustManager implements X509TrustManager{
        @Override
        public void checkClientTrusted(X509Certificate[] arg0,
                String arg1) throws CertificateException {

        }
        @Override
        public void checkServerTrusted(X509Certificate[] arg0,
                String arg1) throws CertificateException {

        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    }
    */
}
