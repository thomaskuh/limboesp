package net.limbomedia.esp.web;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.kuhlins.lib.webkit.HttpUtils;
import org.kuhlins.lib.webkit.HttpUtils.UserAndPass;
import org.kuhlins.lib.webkit.ex.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(200)
public class SecuredAspect {

    @Autowired
    private HttpServletRequest req;

    @Value("${user}")
    private String user;

    @Value("${pass}")
    private String pass;

    @Around("within(@net.limbomedia.esp.web.Secured *)")
    public Object doSecurity(ProceedingJoinPoint pjp) throws Throwable {
        UserAndPass uap = HttpUtils.parseBasicAuth(req.getHeader("Authorization"));
        if (uap == null || !user.equals(uap.getUser()) || !pass.equals(uap.getPass())) {
            throw new UnauthorizedException();
        }
        return pjp.proceed();
    }
}
