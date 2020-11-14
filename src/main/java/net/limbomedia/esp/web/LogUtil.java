package net.limbomedia.esp.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.limbomedia.esp.Loggy;

public class LogUtil {

	public static void log(HttpServletRequest req) {
		if (!Loggy.WEB.isDebugEnabled()) {
			return;
		}

		Loggy.WEB.debug("--------------------------------------------");
		Loggy.WEB.debug("Incoming request ({}): {}.", req.getMethod(), req.getRequestURI());

		Enumeration<String> headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Loggy.WEB.debug("Header: {} -> {}.", headerName, req.getHeader(headerName));
		}

		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			Loggy.WEB.debug("Param: {} -> {}.", paramName, req.getParameter(paramName));
		}
	}
}
