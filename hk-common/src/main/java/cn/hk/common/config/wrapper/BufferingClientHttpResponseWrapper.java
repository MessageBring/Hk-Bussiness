package cn.hk.common.config.wrapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferingClientHttpResponseWrapper
        implements ClientHttpResponse {

    private final ClientHttpResponse response;
    private byte[] body;

    public BufferingClientHttpResponseWrapper(ClientHttpResponse response) throws IOException {
        this.response = response;
        this.body = streamToByteArray(response.getBody());
    }

    @Override
    public InputStream getBody() throws IOException {
        // 每次读取时都从缓存的字节数组创建一个新的 InputStream
        return new ByteArrayInputStream(this.body);
    }

    private byte[] streamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    // 以下方法是对原始 ClientHttpResponse 的代理调用
    @Override
    public org.springframework.http.HttpStatus getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return response.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public org.springframework.http.HttpHeaders getHeaders() {
        return response.getHeaders();
    }
}
