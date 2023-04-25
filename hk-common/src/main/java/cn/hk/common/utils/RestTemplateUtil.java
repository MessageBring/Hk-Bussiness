package cn.hk.common.utils;

import cn.hk.common.config.RestTemplateInterceptor;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class RestTemplateUtil {
    private static final int CONNECTION_TIMEOUT = 2000; // 连接超时时间

    private static final int CONNECTION_REQUEST_TIMEOUT = 2000; // 请求连接超时时间

    private static final int SOCKET_TIMEOUT = 30000; // 等待数据超时时间

    private static final int CONNECTION_MAX_TOTAL = 500; // 所有路由总共最大连接数

    private static final int CONNECTION_MAX_PER_ROUTE = 100; // 每个路由最大连接数

    private static final int SO_TIMEOUT = 30000; // 等待数据超时时间

    // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
    private static final int VALIDATE_AFTER_INACTIVITY = 3000000;

    private static RestTemplate restTemplate;

    static {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();

        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        clientConnectionManager.setMaxTotal(CONNECTION_MAX_TOTAL);
        clientConnectionManager.setDefaultMaxPerRoute(CONNECTION_MAX_PER_ROUTE);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .build();

        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoKeepAlive(false)
                .setSoTimeout(SO_TIMEOUT).build();
        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(clientConnectionManager)
                .setDefaultSocketConfig(socketConfig).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.getInterceptors().add(new RestTemplateInterceptor());
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    /**
     *
     * @param url 请求地址
     * @param httpMethod 请求方式
     * @param httpEntity 请求头和参数装配类
     * @param rClass 指定返回的类型
     * @param <R>
     * @return
     */
    public static <R> R invoke(String url,HttpMethod httpMethod, HttpEntity httpEntity,Class<R> rClass){
        if (StringUtil.isEmpty(url)){
            throw new IllegalArgumentException("request url can't be empty");
        }
        ResponseEntity<R> rResponseEntity;
        try {
            rResponseEntity = restTemplate.exchange(url,httpMethod,httpEntity,rClass);
            log.info("Rest Response:{}",rResponseEntity.getBody());
        }catch (HttpClientErrorException e){
            log.error("Rest exception,status:{},status text:{}",e.getRawStatusCode(),e.getStatusText());
            return null;
        }
        return rResponseEntity.getBody();
    }


    private static <R> R doGetWithHeader(String url, Map<String,String> header, String pathParams,Class<R> rClass){
        String requestUrl = url;
        if (!StringUtil.isEmpty(pathParams)){
            requestUrl = url + "?"+pathParams;
        }
        HttpHeaders headers = new HttpHeaders();
        for (String key:header.keySet()){
            headers.add(key,header.get(key));
        }
        HttpEntity request = new HttpEntity(null,headers);
        return invoke(requestUrl,HttpMethod.GET,request,rClass);
    }

    /**
     * 用于做带自定义header的get请求
     * @param url
     * @param header
     * @param uriParam
     * @param rClass
     * @param <R>
     * @return
     */
    public static <R> R doGetWithHeader(String url,Map<String,String> header,Map<String,String> uriParam,Class<R> rClass){
        String paramStr = null;
        if (uriParam!=null&&uriParam.size()>0) {
            StringBuilder strBuilder = new StringBuilder();
            Iterator<Map.Entry<String,String>> iterator = uriParam.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,String> entry = iterator.next();
                strBuilder.append(entry.getKey());
                strBuilder.append("=");
                strBuilder.append(entry.getValue());
                if (iterator.hasNext()){
                    strBuilder.append("&");
                }
            }
            paramStr = strBuilder.toString();
        }
        return doGetWithHeader(url,header,paramStr,rClass);
    }

    /**
     *
     * @param urlWithParam 带参数的url，例如：https://0.0.0.0/test?test=test&name=name
     * @param <R>
     * @return
     */
    public static <R> R doGet(String urlWithParam,Class<R> rClass){
        return invoke(urlWithParam,HttpMethod.GET,null,rClass);
    }

    /**
     * 可自定义header的post请求
     * @param url
     * @param header
     * @param param
     * @param rClass
     * @param <R>
     * @return
     */
    public static <R> R doPostWithHeader(String url,Map<String,String> header,Map<String,Object> param,Class<R> rClass){
        if (header==null||header.isEmpty()){
            throw new IllegalArgumentException("In this func,header can't be empty");
        }
        HttpHeaders headers = new HttpHeaders();
        for (String key:header.keySet()){
            headers.add(key,header.get(key));
        }
        HttpEntity<Map> request = new HttpEntity<>(param,headers);
        return invoke(url,HttpMethod.POST,request,rClass);
    }

    /**
     * 普通post请求
     * @param url
     * @param param
     * @param rClass
     * @param <R>
     * @return
     */
    public static <R> R doPost(String url,Map<String,Object> param,Class<R> rClass){
        HttpEntity<Map> entity = new HttpEntity<>(param);
        return invoke(url,HttpMethod.POST,entity,rClass);
    }

}
