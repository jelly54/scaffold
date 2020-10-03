package ${groupId}.aop;

import ${groupId}.util.IPUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * 日志aop
 *
 * @author zhangguodong
 */
@Aspect
@Component
public class LoggerAspect {

    private static Logger log = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(* ${groupId}.controller.*.*(..))")
    private void controllerPointCut() {
    }

    @Around("controllerPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        UUID uuid = UUID.randomUUID();
        long start = System.currentTimeMillis();
        log.info("requestId: {}, start time: {}", uuid, start);

        // 记录请求ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            log.info("requestId: {}, {} {}, ip: {}", uuid, method, uri, IPUtil.getIp(request));
        }

        // 记录请求路径、方法、参数
        String fullMethod = pjp.getSignature().toString();
        Object[] args = pjp.getArgs();

        log.info("requestId: {}, class-method: {}, args: {}", uuid, fullMethod, Arrays.toString(args));

        Object retVal = pjp.proceed();

        log.info("requestId: {}, class-method: {}, result:{}", uuid, fullMethod, retVal.toString());

        long end = System.currentTimeMillis();
        log.info("requestId: {}, end time: {}, cost:{}ms", uuid, end, end - start);

        return retVal;
    }

    @AfterThrowing(pointcut = "controllerPointCut()", throwing = "ex")
    public void doAfterThrowing(Exception ex) {
        log.error("error.", ex);
    }
}
