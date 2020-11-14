package net.limbomedia.esp.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuhlins.webkit.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ExceptionHandler implements HandlerExceptionResolver {

	private static final Logger L = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		try {
			HttpUtils.handleException(ex, response);
		} catch (IOException e) {
			L.error("Exception while handling exception. {}.", e.getMessage(), e);
		}
		return null;
	}

}
