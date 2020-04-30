package com.example.ecs.logging.java.sample;

import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Log4j2
public class MdcFilter implements Filter {
        public static final String KEY = "x-test-id"; //logback内から取得する際のキー

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
                throws IOException, ServletException {
            try {
                log.info("MDC Filtered...");
                MDC.put(KEY, UUID.randomUUID().toString());
                filterChain.doFilter(servletRequest, servletResponse);
            } finally {
                MDC.remove(KEY);
            }
        }
}
