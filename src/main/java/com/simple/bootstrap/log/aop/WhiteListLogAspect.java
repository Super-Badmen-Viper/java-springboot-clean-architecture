package com.simple.bootstrap.log.aop;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.json.JSONUtil;

import com.simple.bootstrap.log.properties.LoggingProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class WhiteListLogAspect {

    @Autowired
    private LoggingProperties loggingProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Around("execution(public * com.simple.api.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();

        String requestURI = request.getRequestURI();
        if (!shouldLog(requestURI)) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            addLog(joinPoint,
                    JSONUtil.toJsonStr(result),
                    endTime - startTime
            );
        }
    }


    public void addLog(JoinPoint joinPoint, String outParams, long time) {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        log.info("""
                        
                        \r=======================================
                        \r\
                        请求地址:{}
                        \r\
                        请求方式:{}
                        \r\
                        请求类方法:{}
                        \r\
                        请求方法参数:{}
                        \r\
                        返回报文:{}
                        \r\
                        处理耗时:{} ms\s
                        \r\
                        =======================================
                        \r""",
                request.getRequestURI(),
                request.getMethod(),
                joinPoint.getSignature(),
                JSONUtil.toJsonStr(filterArgs(joinPoint.getArgs())),
                outParams,
                time
        );
    }

    private List<Object> filterArgs(Object[] args) {
        return Arrays.stream(args)
                .filter(arg -> !isExcludedType(arg))
                .collect(Collectors.toList());
    }

    private boolean isExcludedType(Object obj) {
        return obj instanceof HttpServletRequest
                || obj instanceof HttpServletResponse
                || obj instanceof MultipartFile;
    }

    private boolean shouldLog(String requestURI) {
        List<String> paths = Optional.ofNullable(loggingProperties.getIncludePaths())
                .orElse(Collections.emptyList());
        return paths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }
}
