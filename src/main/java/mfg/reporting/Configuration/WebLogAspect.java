package mfg.reporting.Configuration;

import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.pojo.ClickRate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author dk
 * @version 1.0.0
 * ClassName WebLogAspect.java
 * Description log 日志 以及点击率统计；
 * createTime 2022年10月13日 10:47:00
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    private static Logger clickLogger = LoggerFactory.getLogger("clickLogger");

    @Resource
    public JwtUtils jwtUtils;

    /** 以 controller 包下定义的所有请求为切入点 */
    @Pointcut("execution(public * mfg.reporting.controller..*.*(..))")
    public void webLog() {}
    /**
     * 在切点之前织入
     * param joinPoint
     * throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        SystemLog annotation = signature.getMethod().getAnnotation(SystemLog.class);

        if (attributes != null) {
          HttpServletRequest request = attributes.getRequest();

        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
            // 创建一个SimpleDateFormat对象，用于指定的日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            // 格式化当前日期和时间
            String formattedDate = sdf.format(System.currentTimeMillis());
            log.info("当前日期    : {}", formattedDate);
            // 打印请求的 User
            String name = jwtUtils.getUsernameFromToken();
            log.info("User             : {}",name);
        //判断是否有注解信息，如果有则打印，没有则不打印
        if(annotation!=null && !annotation.function_id().isEmpty()){
        log.info("OperationType    : {}", annotation.operateType());
        //点击率兼容；错误不影响整体系统使用；
        ClickRate clickRate = new ClickRate();
            clickRate.setCname(name);
            clickRate.setFunctionId(annotation.function_id());
        //link Controller文件名+ 为当前方法名
        String linkName = joinPoint.getSignature().getDeclaringTypeName().replace("mfg.reporting.controller.", "") + "." + joinPoint.getSignature().getName();
            clickRate.setLink(linkName);
            clickRate.setFunctionType(annotation.operateType());
            clickRate.setRouteName(request.getHeader("routename"));
            clickRate.setUpdateTime(formattedDate);
            clickLogger.info(JSON.toJSONString(clickRate));
        }
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        }
    }

       /**
     * 只有正常返回才会执行此方法
     * 如果程序执行失败，则不执行此方法
     */
    @AfterReturning(returning = "returnVal", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object returnVal) {
      log.info("接口调用成功");
    }

    /**
     * 当接口报错时执行此方法
     */
    @AfterThrowing(pointcut = "webLog()")
    public void doAfterThrowing(JoinPoint joinPoint) {
      log.info("接口调用失败");
    }


    /**
     * 在切点之后织入
     * throws Throwable
     */
    @After("webLog()")
    public void doAfter() {
      log.info("=========================================== End ===========================================");
        // 每个请求之间空一行
        log.info("");
    }
    /**
     * 环绕
     * @param proceedingJoinPoint
     * return
     * throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        // String s = JSONObject.toJSONString(result.toString());
        // log.info("Return Results : {}", s);
        // 执行耗时
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }
}
