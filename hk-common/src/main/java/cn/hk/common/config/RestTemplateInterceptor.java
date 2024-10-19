package cn.hk.common.config;

import cn.hk.common.config.wrapper.BufferingClientHttpResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("Rest request invoke,Method:{},Url:{}",request.getMethod(),request.getURI());
        log.info("Rest request body:{}",new String(body));
        ClientHttpResponse response = execution.execute(request,body);

        // 包装响应，保证响应流可以多次读取
        ClientHttpResponse wrappedResponse = new BufferingClientHttpResponseWrapper(response);

        // 读取并打印响应内容
        String responseBody = new BufferedReader(new InputStreamReader(wrappedResponse.getBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        log.info("Rest response body: {}", responseBody);
        return wrappedResponse;
    }
}
