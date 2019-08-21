package wan.dianjie.wandj.config.log;

import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志切面
 *
 * @author wan dianjie
 * @date 2019-08-21 11:00
 */
@Slf4j
@Aspect
@Component
public class LogControllerAspect {
  @Pointcut(value = "@annotation(wan.dianjie.wandj.config.log.LogControllerFilter)")
  public void logPointCut (){

  }
  @Around("logPointCut()")
  public Object around (ProceedingJoinPoint point) throws Throwable {
    Object result = null ;
    try{
      // 执行方法
      result = point.proceed();
      // 保存请求日志
      saveRequestLog(point);
    } catch (Exception e){
      // 保存异常日志
      saveExceptionLog(point,e.getMessage());
    }
    return result;
  }
  private void saveExceptionLog (ProceedingJoinPoint point,String exeMsg){
    log.info("捕获异常:"+exeMsg);
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    log.info("请求路径:"+request.getRequestURL());
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    log.info("请求方法:"+method.getName());
    // 获取方法上LogFilter注解
    LogControllerFilter logControllerFilter = method.getAnnotation(LogControllerFilter.class);
    String value = logControllerFilter.value() ;
    log.info("模块描述:"+value);
    Object[] args = point.getArgs();
    log.info("请求参数:"+ JSONObject.toJSONString(args));
  }
  private void saveRequestLog (ProceedingJoinPoint point){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    log.info("请求路径:"+request.getRequestURL());
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    log.info("请求方法:"+method.getName());
    // 获取方法上LogFilter注解
    LogControllerFilter logControllerFilter = method.getAnnotation(LogControllerFilter.class);
    String value = logControllerFilter.value() ;
    log.info("模块描述:"+value);
    Object[] args = point.getArgs();
    log.info("请求参数:"+ JSONObject.toJSONString(args));
  }
}
