package com.project.QR.aop;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class LogAop {
  /**
   * com.project.QR 이하 controller 포인트컷 적용
   */
  @Pointcut("execution(* com.project.QR.*.controller..*.*(..))")
  private void cut(){}

  /**
   * 포인트컷에 의해 필터링된 경로로 들어오는 경우 메서드 호출 전에 적용
   */
  @Before("cut()")
  public void beforeParameterLog(JoinPoint joinPoint) {
    Method method = getMethod(joinPoint);
    log.info("======= method name = {} =======", method.getName());

    Object[] args = joinPoint.getArgs();
    if (args.length <= 0) log.info("no parameter");
    for (Object arg : args) {
      log.info("parameter type = {}", arg.getClass().getSimpleName());
      log.info("parameter value = {}", arg);
    }
  }

  /**
   *
   * 포인트컷에 의해 필터링된 경로로 들어오는 경우 메서드 리턴 후에 적용
   */
  @AfterReturning(value = "cut()", returning = "returnObj")
  public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
    Method method = getMethod(joinPoint);
    log.info("======= method name = {} =======", method.getName());

    log.info("return type = {}", returnObj.getClass().getSimpleName());
    log.info("return value = {}", returnObj);
  }

  /**
   * 조인 포인트로 메서드 정보 가져오기
   */
  private Method getMethod(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    return signature.getMethod();
  }
}
