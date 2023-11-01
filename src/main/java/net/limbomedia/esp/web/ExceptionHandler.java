package net.limbomedia.esp.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.kuhlins.lib.webkit.HttpExceptionizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger L = LoggerFactory.getLogger(ExceptionHandler.class);

    private HttpExceptionizer exceptionizer = new HttpExceptionizer();

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            exceptionizer.handleException(ex, response);
        } catch (IOException e) {
            L.error("Exception while handling exception. {}.", e.getMessage(), e);
        }
        return null;
    }
}
