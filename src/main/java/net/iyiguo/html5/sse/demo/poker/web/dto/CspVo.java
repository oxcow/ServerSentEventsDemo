package net.iyiguo.html5.sse.demo.poker.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @author William.li
 * @date 2021/8/5
 */
public class CspVo {

    @JsonAlias("csp-report")
    private CspReport cspReport;

    public CspReport getCspReport() {
        return cspReport;
    }

    public void setCspReport(CspReport cspReport) {
        this.cspReport = cspReport;
    }

    @Override
    public String toString() {
        return "CspVo{" +
                "cspReport=" + cspReport +
                '}';
    }
}
