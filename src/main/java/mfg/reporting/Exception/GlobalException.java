package mfg.reporting.Exception;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// import mfg.reporting.Exception.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理 配置
 *
 * @author javadog
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {
	
    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUrl, e.getMethod());
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseResult<String> handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return ObjectUtil.isNotNull(code) ? ResponseResult.fail(code, e.getMessage()) : ResponseResult.fail(e.getMessage());
    }


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<String> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestUrl, e);
        return ResponseResult.fail(e.getMessage());
    }



    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestUri, e);
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<String> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResponseResult.fail(message);
    }
}