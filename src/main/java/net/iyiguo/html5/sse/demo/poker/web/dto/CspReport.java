package net.iyiguo.html5.sse.demo.poker.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @author William.li
 * @date 2021/8/5
 */
public class CspReport {
    @JsonAlias("blocked-uri")
    private String blockedUri;

    private String disposition;

    @JsonAlias("document-uri")
    private String documentUri;

    @JsonAlias("effective-directive")
    private String effectiveDirective;

    @JsonAlias("line-number")
    private String lineNumber;

    @JsonAlias("original-policy")
    private String originalPolicy;

    private String referrer;

    @JsonAlias("script-sample")
    private String scriptSample;

    @JsonAlias("source-file")
    private String sourceFile;

    @JsonAlias("status-code")
    private String statusCode;

    @JsonAlias("violated-directive")
    private String violatedDirective;

    public String getBlockedUri() {
        return blockedUri;
    }

    public void setBlockedUri(String blockedUri) {
        this.blockedUri = blockedUri;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getDocumentUri() {
        return documentUri;
    }

    public void setDocumentUri(String documentUri) {
        this.documentUri = documentUri;
    }

    public String getEffectiveDirective() {
        return effectiveDirective;
    }

    public void setEffectiveDirective(String effectiveDirective) {
        this.effectiveDirective = effectiveDirective;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getOriginalPolicy() {
        return originalPolicy;
    }

    public void setOriginalPolicy(String originalPolicy) {
        this.originalPolicy = originalPolicy;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getScriptSample() {
        return scriptSample;
    }

    public void setScriptSample(String scriptSample) {
        this.scriptSample = scriptSample;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getViolatedDirective() {
        return violatedDirective;
    }

    public void setViolatedDirective(String violatedDirective) {
        this.violatedDirective = violatedDirective;
    }

    @Override
    public String toString() {
        return "CspReportVo{" +
                "blockedUri='" + blockedUri + '\'' +
                ", disposition='" + disposition + '\'' +
                ", documentUri='" + documentUri + '\'' +
                ", effectiveDirective='" + effectiveDirective + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", originalPolicy='" + originalPolicy + '\'' +
                ", referrer='" + referrer + '\'' +
                ", scriptSample='" + scriptSample + '\'' +
                ", sourceFile='" + sourceFile + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", violatedDirective='" + violatedDirective + '\'' +
                '}';
    }
}
