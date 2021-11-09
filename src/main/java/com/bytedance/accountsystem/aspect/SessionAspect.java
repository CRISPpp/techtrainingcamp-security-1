//package com.bytedance.accountsystem.aspect;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashSet;
//import java.util.Set;
//
//@Aspect
//@Component
//public class SessionAspect {
//    private static Set<String> noTokenMethods = new HashSet<String>() {
//        {
//            add("login");
//            add("register");
//        }
//    };
//
//
//    @Before("execution(* com.bytedance.accountsystem.controller.*.*(..))")
//    public void checkToken(JoinPoint joinPoint) throws Throwable {
//        String methodName = joinPoint.getSignature().getName();
////        if (!noTokenMethods.contains(methodName)) {
//            HttpServletRequest token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).
//                    getRequest();
//            System.out.println(token.);
////        }
//    }
//}
