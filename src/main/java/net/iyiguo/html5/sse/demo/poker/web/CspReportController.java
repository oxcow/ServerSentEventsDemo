package net.iyiguo.html5.sse.demo.poker.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.iyiguo.html5.sse.demo.poker.web.dto.CspVo;

/**
 * <a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/CSP">内容安全策略（CSP）</a>
 *
 * @author William.li
 * @date 2021/8/5
 * @see net.iyiguo.html5.sse.demo.poker.config.HttpHeaderFilter
 */
@RestController
@RequestMapping("/csp-report")
public class CspReportController {

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/", consumes = "application/csp-report")
    public boolean report(HttpServletRequest httpRequest) throws Exception {
        Optional<String> msgOptional = httpRequest.getReader().lines().findFirst();
        if (msgOptional.isPresent()) {
            CspVo cspVo1 = objectMapper.readValue(msgOptional.get(), CspVo.class);
            System.out.println(cspVo1);
        }
        return true;
    }
}
