package com.amr.TaskManager.middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

public class LoggingFilter implements Filter {


    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        logger.info("Incoming request: URL={} Method={} IP={}",
                httpRequest.getRequestURL(),
                httpRequest.getMethod(),
                httpRequest.getRemoteAddr());

        chain.doFilter(request, response); // Proceed to the next filter or handler

        logger.info("Outgoing response: Status={}", httpResponse.getStatus());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
