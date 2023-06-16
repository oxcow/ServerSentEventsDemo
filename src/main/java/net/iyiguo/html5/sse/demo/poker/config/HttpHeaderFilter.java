package net.iyiguo.html5.sse.demo.poker.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author William.li
 * @date 2021/8/5
 */
public class HttpHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Content-Security-Policy",
                "default-src 'self';script-src 'self' libs.baidu.com 'unsafe-inline'; style-src 'self' https://cdn.bootcdn.net https://unpkg.com");
        // only report
        httpServletResponse.setHeader("Content-Security-Policy-Report-Only",
                httpServletResponse.getHeader("Content-Security-Policy") + ";report-uri /csp-report/");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
