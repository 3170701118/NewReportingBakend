package mfg.reporting.Configuration;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class HttpUtil {

    /**
     * HTTP 请求配置
     */
    private static class HttpConfig implements Serializable {
        private int connectTimeout = 5000;
        private int readTimeout = 10000;
        private int maxRetries = 3;
        private long retryDelay = 1000L;
        private boolean enableLog = true;
        private boolean enableGzip = true;
        private boolean keepAlive = true;
        private String defaultCharset = "UTF-8";

        // Getters and Setters
        public int getConnectTimeout() { return connectTimeout; }
        public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }
        public int getReadTimeout() { return readTimeout; }
        public void setReadTimeout(int readTimeout) { this.readTimeout = readTimeout; }
        public int getMaxRetries() { return maxRetries; }
        public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }
        public long getRetryDelay() { return retryDelay; }
        public void setRetryDelay(long retryDelay) { this.retryDelay = retryDelay; }
        public boolean isEnableLog() { return enableLog; }
        public void setEnableLog(boolean enableLog) { this.enableLog = enableLog; }
        public boolean isEnableGzip() { return enableGzip; }
        public void setEnableGzip(boolean enableGzip) { this.enableGzip = enableGzip; }
        public boolean isKeepAlive() { return keepAlive; }
        public void setKeepAlive(boolean keepAlive) { this.keepAlive = keepAlive; }
        public String getDefaultCharset() { return defaultCharset; }
        public void setDefaultCharset(String defaultCharset) { this.defaultCharset = defaultCharset; }
    }

    private final HttpConfig config = new HttpConfig();

    private static final Map<String, String> DEFAULT_HEADERS = new HashMap<>();
    static {
        DEFAULT_HEADERS.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        DEFAULT_HEADERS.put("Accept", "application/json, text/plain, */*");
        DEFAULT_HEADERS.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
    }

    @Value("${http.connect-timeout:5000}")
    private int connectTimeout;

    @Value("${http.read-timeout:10000}")
    private int readTimeout;

    @Value("${http.max-retries:3}")
    private int maxRetries;

    @Value("${http.enable-log:true}")
    private boolean enableLog;

    @PostConstruct
    public void init() {
        config.setConnectTimeout(connectTimeout);
        config.setReadTimeout(readTimeout);
        config.setMaxRetries(maxRetries);
        config.setEnableLog(enableLog);
        log.info("HTTP工具类初始化完成，配置: {}", config);
    }

    // ============== 基本类型方法 ==============

    /**
     * GET 请求 - 返回原始字符串
     */
    public String getForString(String url) {
        return getForString(url, Collections.emptyMap());
    }

    /**
     * GET 请求 - 返回原始字符串（带请求头）
     */
    public String getForString(String url, Map<String, String> headers) {
        return executeRequest(() -> {
            validateUrl(url);
            long startTime = System.currentTimeMillis();

            if (config.isEnableLog()) {
                log.info("发送 GET 请求，URL: {}, Headers: {}", url, headers);
            }

            cn.hutool.http.HttpRequest request = cn.hutool.http.HttpRequest.get(url)
                    .timeout(config.getConnectTimeout())
                    .charset(StandardCharsets.UTF_8)
                    .setReadTimeout(config.getReadTimeout());

            addDefaultHeaders(request);
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(request::header);
            }

            // 使用 try-with-resources 确保 HttpResponse 正确关闭
            try (cn.hutool.http.HttpResponse response = request.execute()) {
                String result = response.body();

                long duration = System.currentTimeMillis() - startTime;
                if (config.isEnableLog()) {
                    log.info("GET 请求完成，耗时: {} ms，URL: {}，状态码: {}",
                            duration, url, response.getStatus());
                }

                // 检查 HTTP 状态码
                checkHttpStatus(response, url, "GET");
                return result;
            }
        }, url, "GET");
    }

    /**
     * GET 请求 - 返回指定类型的对象
     */
    public <T> T getForObject(String url, Class<T> clazz) {
        return getForObject(url, Collections.emptyMap(), clazz);
    }

    /**
     * GET 请求 - 返回指定类型的对象（带请求头）
     */
    public <T> T getForObject(String url, Map<String, String> headers, Class<T> clazz) {
        String result = getForString(url, headers);
        return parseResponse(result, clazz);
    }

    /**
     * GET 请求 - 返回复杂类型（如 List<T>）
     */
    public <T> T getForObject(String url, TypeReference<T> typeReference) {
        return getForObject(url, Collections.emptyMap(), typeReference);
    }

    /**
     * GET 请求 - 返回复杂类型（带请求头）
     */
    public <T> T getForObject(String url, Map<String, String> headers, TypeReference<T> typeReference) {
        String result = getForString(url, headers);
        return parseResponse(result, typeReference);
    }

    // ============== POST 请求 ==============

    /**
     * POST 请求 - 返回原始字符串
     */
    public String postForString(String url, String body) {
        return postForString(url, body, Collections.emptyMap());
    }

    /**
     * POST 请求 - 返回原始字符串（带请求头）
     */
    public String postForString(String url, String body, Map<String, String> headers) {
        return executeRequest(() -> {
            validateUrl(url);
            validateJsonBody(body);

            long startTime = System.currentTimeMillis();
            if (config.isEnableLog()) {
                log.info("发送 POST 请求，URL: {}, Body长度: {}", url, body.length());
            }

            cn.hutool.http.HttpRequest request = cn.hutool.http.HttpRequest.post(url)
                    .timeout(config.getConnectTimeout())
                    .charset(StandardCharsets.UTF_8)
                    .body(body, "application/json;charset=UTF-8")
                    .setReadTimeout(config.getReadTimeout());

            addDefaultHeaders(request);
            request.header("Content-Type", "application/json;charset=UTF-8");
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(request::header);
            }

            // 使用 try-with-resources 确保 HttpResponse 正确关闭
            try (cn.hutool.http.HttpResponse response = request.execute()) {
                String result = response.body();

                long duration = System.currentTimeMillis() - startTime;
                if (config.isEnableLog()) {
                    log.info("POST 请求完成，耗时: {} ms，URL: {}，状态码: {}",
                            duration, url, response.getStatus());
                }

                // 检查 HTTP 状态码
                checkHttpStatus(response, url, "POST");
                return result;
            }
        }, url, "POST");
    }

    /**
     * POST 请求 - 返回指定类型的对象
     */
    public <T> T postForObject(String url, String body, Class<T> clazz) {
        return postForObject(url, body, Collections.emptyMap(), clazz);
    }

    /**
     * POST 请求 - 返回指定类型的对象（带请求头）
     */
    public <T> T postForObject(String url, String body, Map<String, String> headers, Class<T> clazz) {
        String result = postForString(url, body, headers);
        return parseResponse(result, clazz);
    }

    /**
     * POST 请求 - 返回复杂类型
     */
    public <T> T postForObject(String url, String body, TypeReference<T> typeReference) {
        return postForObject(url, body, Collections.emptyMap(), typeReference);
    }

    /**
     * POST 请求 - 返回复杂类型（带请求头）
     */
    public <T> T postForObject(String url, String body, Map<String, String> headers, TypeReference<T> typeReference) {
        String result = postForString(url, body, headers);
        return parseResponse(result, typeReference);
    }

    // ============== POST Form 请求 ==============

    /**
     * POST Form 请求 - 返回原始字符串
     */
    public String postFormForString(String url, Map<String, Object> formData) {
        return postFormForString(url, formData, Collections.emptyMap());
    }

    /**
     * POST Form 请求 - 返回原始字符串（带请求头）
     */
    public String postFormForString(String url, Map<String, Object> formData, Map<String, String> headers) {
        return executeRequest(() -> {
            validateUrl(url);

            long startTime = System.currentTimeMillis();
            if (config.isEnableLog()) {
                log.info("发送 POST Form 请求，URL: {}, FormData大小: {}", url,
                        formData != null ? formData.size() : 0);
            }

            cn.hutool.http.HttpRequest request = cn.hutool.http.HttpRequest.post(url)
                    .timeout(config.getConnectTimeout())
                    .charset(StandardCharsets.UTF_8)
                    .setReadTimeout(config.getReadTimeout());

            if (formData != null && !formData.isEmpty()) {
                request.form(formData);
            }

            addDefaultHeaders(request);
            request.header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(request::header);
            }

            // 使用 try-with-resources 确保 HttpResponse 正确关闭
            try (cn.hutool.http.HttpResponse response = request.execute()) {
                String result = response.body();

                long duration = System.currentTimeMillis() - startTime;
                if (config.isEnableLog()) {
                    log.info("POST Form 请求完成，耗时: {} ms，URL: {}，状态码: {}",
                            duration, url, response.getStatus());
                }

                // 检查 HTTP 状态码
                checkHttpStatus(response, url, "POST");
                return result;
            }
        }, url, "POST");
    }

    // ============== PUT 请求 ==============

    /**
     * PUT 请求 - 返回原始字符串
     */
    public String putForString(String url) {
        return putForString(url, null, Collections.emptyMap());
    }

    /**
     * PUT 请求 - 返回原始字符串（带请求体和请求头）
     */
    public String putForString(String url, String body, Map<String, String> headers) {
        return executeRequest(() -> {
            validateUrl(url);

            long startTime = System.currentTimeMillis();
            if (config.isEnableLog()) {
                log.info("发送 PUT 请求，URL: {}", url);
            }

            cn.hutool.http.HttpRequest request = cn.hutool.http.HttpRequest.put(url)
                    .timeout(config.getConnectTimeout())
                    .charset(StandardCharsets.UTF_8)
                    .setReadTimeout(config.getReadTimeout());

            if (StrUtil.isNotBlank(body)) {
                request.body(body, "application/json;charset=UTF-8");
                request.header("Content-Type", "application/json;charset=UTF-8");
            }

            addDefaultHeaders(request);
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(request::header);
            }

            // 使用 try-with-resources 确保 HttpResponse 正确关闭
            try (cn.hutool.http.HttpResponse response = request.execute()) {
                String result = response.body();

                long duration = System.currentTimeMillis() - startTime;
                if (config.isEnableLog()) {
                    log.info("PUT 请求完成，耗时: {} ms，URL: {}，状态码: {}",
                            duration, url, response.getStatus());
                }

                // 检查 HTTP 状态码
                checkHttpStatus(response, url, "PUT");
                return result;
            }
        }, url, "PUT");
    }

    // ============== DELETE 请求 ==============

    /**
     * DELETE 请求 - 返回原始字符串
     */
    public String deleteForString(String url) {
        return deleteForString(url, Collections.emptyMap());
    }

    /**
     * DELETE 请求 - 返回原始字符串（带请求头）
     */
    public String deleteForString(String url, Map<String, String> headers) {
        return executeRequest(() -> {
            validateUrl(url);

            long startTime = System.currentTimeMillis();
            if (config.isEnableLog()) {
                log.info("发送 DELETE 请求，URL: {}", url);
            }

            cn.hutool.http.HttpRequest request = cn.hutool.http.HttpRequest.delete(url)
                    .timeout(config.getConnectTimeout())
                    .charset(StandardCharsets.UTF_8)
                    .setReadTimeout(config.getReadTimeout());

            addDefaultHeaders(request);
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(request::header);
            }

            // 使用 try-with-resources 确保 HttpResponse 正确关闭
            try (cn.hutool.http.HttpResponse response = request.execute()) {
                String result = response.body();

                long duration = System.currentTimeMillis() - startTime;
                if (config.isEnableLog()) {
                    log.info("DELETE 请求完成，耗时: {} ms，URL: {}，状态码: {}",
                            duration, url, response.getStatus());
                }

                // 检查 HTTP 状态码
                checkHttpStatus(response, url, "DELETE");
                return result;
            }
        }, url, "DELETE");
    }

    /**
     * DELETE 请求 - 返回指定类型的对象
     */
    public <T> T deleteForObject(String url, Class<T> clazz) {
        String result = deleteForString(url);
        return parseResponse(result, clazz);
    }

    // ============== 辅助方法 ==============

    /**
     * 执行带重试机制的请求
     */
    private String executeRequest(HttpRequestAction action, String url, String method) {
        int retries = 0;
        Exception lastException = null;

        while (retries <= config.getMaxRetries()) {
            try {
                return action.execute();
            } catch (Exception e) {
                lastException = e;
                retries++;

                if (retries <= config.getMaxRetries()) {
                    log.warn("HTTP请求失败，正在重试 ({}/{})，URL: {}, 方法: {}, 错误: {}",
                            retries, config.getMaxRetries(), url, method, e.getMessage());

                    try {
                        TimeUnit.MILLISECONDS.sleep(config.getRetryDelay());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new HttpException("请求被中断", ie);
                    }
                }
            }
        }

        log.error("HTTP请求失败，已达到最大重试次数 {}，URL: {}, 方法: {}",
                config.getMaxRetries(), url, method, lastException);
        throw new HttpException(String.format("请求失败，已达到最大重试次数: %s %s", method, url), lastException);
    }

    /**
     * 添加默认请求头
     */
    private void addDefaultHeaders(cn.hutool.http.HttpRequest request) {
        DEFAULT_HEADERS.forEach(request::header);

        if (config.isEnableGzip()) {
            request.header("Accept-Encoding", "gzip, deflate");
        }

        if (!config.isKeepAlive()) {
            request.header("Connection", "close");
        }
    }

    /**
     * 检查 HTTP 状态码
     */
    private void checkHttpStatus(cn.hutool.http.HttpResponse response, String url, String method) {
        int status = response.getStatus();
        if (status >= 400) {
            String message = String.format("HTTP请求失败，状态码: %d，URL: %s，方法: %s",
                    status, url, method);
            log.warn(message);

            if (status >= 500) {
                // 服务器错误，可以抛出异常或记录更详细的日志
                throw new HttpException("服务器错误: " + status);
            }
        }
    }

    /**
     * 解析响应 - 使用 Class 类型
     */
    private <T> T parseResponse(String response, Class<T> clazz) {
        if (response == null || response.isEmpty()) {
            log.warn("HTTP响应为空");
            return null;
        }

        try {
            // 如果是String类型，直接返回
            if (String.class.equals(clazz)) {
                return clazz.cast(response);
            }

            // 其他类型使用JSON转换
            return JSONUtil.toBean(response, clazz);
        } catch (Exception e) {
            log.error("响应解析失败，响应内容: {}, 目标类型: {}, 错误: {}",
                    StrUtil.sub(response, 0, 1000), clazz.getSimpleName(), e.getMessage());
            throw new HttpException("响应解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 解析响应 - 使用 TypeReference 类型
     */
    private <T> T parseResponse(String response, TypeReference<T> typeReference) {
        if (response == null || response.isEmpty()) {
            log.warn("HTTP响应为空");
            return null;
        }

        try {
            // 如果是String类型，直接返回
            if (String.class.equals(typeReference.getType())) {
                return (T) response;
            }

            // 使用TypeReference进行复杂类型转换
            return JSONUtil.toBean(response, typeReference.getType(), false);
        } catch (Exception e) {
            log.error("响应解析失败，响应内容: {}, 错误: {}",
                    StrUtil.sub(response, 0, 1000), e.getMessage());
            throw new HttpException("响应解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 解析响应 - 使用 Type 类型
     */
    private <T> T parseResponse(String response, Type type) {
        if (response == null || response.isEmpty()) {
            log.warn("HTTP响应为空");
            return null;
        }

        try {
            // 如果是String类型，直接返回
            if (String.class.equals(type)) {
                return (T) response;
            }

            // 使用Type进行复杂类型转换
            return JSONUtil.toBean(response, type, false);
        } catch (Exception e) {
            log.error("响应解析失败，响应内容: {}, 错误: {}",
                    StrUtil.sub(response, 0, 1000), e.getMessage());
            throw new HttpException("响应解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 验证URL
     */
    private void validateUrl(String url) {
        if (StrUtil.isBlank(url)) {
            throw new IllegalArgumentException("URL不能为空");
        }

        // 基本URL格式验证
        if (!url.matches("^(http|https)://.+$")) {
            throw new IllegalArgumentException("URL格式不正确: " + url);
        }
    }

    /**
     * 验证JSON请求体
     */
    private void validateJsonBody(String body) {
        if (StrUtil.isNotBlank(body) && !JSONUtil.isJson(body)) {
            throw new IllegalArgumentException("请求体不是有效的JSON格式");
        }
    }

    // ============== 兼容旧版本的方法 ==============

    /**
     * @deprecated 使用 {@link #getForObject(String, Class)} 或 {@link #getForString(String)}
     */
    @Deprecated
    public <T> T sendGet(String url) {
        String result = getForString(url);
        if (result == null) return null;
        return (T) JSONUtil.parse(result);
    }

    /**
     * @deprecated 使用 {@link #getForObject(String, Map, Class)} 或 {@link #getForString(String, Map)}
     */
    @Deprecated
    public <T> T sendGet(String url, Map<String, String> headers) {
        String result = getForString(url, headers);
        if (result == null) return null;
        return (T) JSONUtil.parse(result);
    }

    /**
     * @deprecated 使用 {@link #postForObject(String, String, Class)} 或 {@link #postForString(String, String)}
     */
    @Deprecated
    public <T> T sendPost(String url, String body) {
        String result = postForString(url, body);
        if (result == null) return null;
        return (T) JSONUtil.parse(result);
    }

    /**
     * @deprecated 使用 {@link #putForString(String)}
     */
    @Deprecated
    public <T> T sendPut(String url) {
        String result = putForString(url);
        if (result == null) return null;
        return (T) JSONUtil.parse(result);
    }

    /**
     * @deprecated 使用 {@link #deleteForString(String)} 或 {@link #deleteForObject(String, Class)}
     */
    @Deprecated
    public <T> T sendDelete(String url) {
        String result = deleteForString(url);
        if (result == null) return null;
        return (T) JSONUtil.parse(result);
    }

    /**
     * @deprecated 使用 {@link #parseResponse(String, Class)} 或 {@link #parseResponse(String, TypeReference)}
     */
    @Deprecated
    public <T> T stringToJson(String str) {
        if (str == null || str.isEmpty()) {
            log.error("stringToJson: 输入字符串为空");
            return null;
        }
        return (T) JSONUtil.parse(str);
    }

    // ============== 其他实用方法 ==============

    /**
     * 设置HTTP配置
     */
    public void setConfig(HttpConfig config) {
        if (config != null) {
            if (config.getConnectTimeout() > 0) {
                this.config.setConnectTimeout(config.getConnectTimeout());
            }
            if (config.getReadTimeout() > 0) {
                this.config.setReadTimeout(config.getReadTimeout());
            }
            if (config.getMaxRetries() >= 0) {
                this.config.setMaxRetries(config.getMaxRetries());
            }
            this.config.setEnableLog(config.isEnableLog());
            this.config.setEnableGzip(config.isEnableGzip());
            this.config.setKeepAlive(config.isKeepAlive());
            if (StrUtil.isNotBlank(config.getDefaultCharset())) {
                this.config.setDefaultCharset(config.getDefaultCharset());
            }
        }
    }

    /**
     * 获取当前配置
     */
    public HttpConfig getConfig() {
        return config;
    }

    /**
     * HTTP请求执行接口
     */
    @FunctionalInterface
    private interface HttpRequestAction {
        String execute() throws Exception;
    }

    /**
     * 自定义HTTP异常
     */
    public static class HttpException extends RuntimeException {
        public HttpException(String message) {
            super(message);
        }

        public HttpException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}