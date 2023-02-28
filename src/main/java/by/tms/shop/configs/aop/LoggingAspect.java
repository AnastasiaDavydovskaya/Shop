package by.tms.shop.configs.aop;

import by.tms.shop.repositories.UserRepository;
import by.tms.shop.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private final UserRepository userRepository;

    public LoggingAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Pointcut("execution(* by.tms.shop.services.impl.*Service.*(..))")
    public void services() {
    }

    @Pointcut("execution(* by.tms.shop.repositories.*Repository.*(..))")
    public void repositories() {
    }

    @Around("services() || repositories()")
    public Object aroundServices(ProceedingJoinPoint proceedingJoinPoint) {
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object result = null;
        if (!Objects.isNull(authentication)) {
            log.info("User '{}' called method '{}.{}': data {} of starting task",
                    authentication.getName(), className, methodName, LocalTime.now());
        }
        try {
            result = proceedingJoinPoint.proceed();
            if (!Objects.isNull(authentication)) {
                log.info("Data {} for user '{}' of '{}.{}' method execution with result: {}",
                        LocalTime.now(), authentication.getName(), className, methodName, result);
            }
        } catch (Throwable e) {
            log.error("Error {} during method '{}.{}' call", e.getMessage(), className, methodName);
        }

        return result;
    }
}
