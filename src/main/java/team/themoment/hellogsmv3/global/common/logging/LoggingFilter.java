package team.themoment.hellogsmv3.global.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        long startTime = System.currentTimeMillis();
        UUID logId = UUID.randomUUID();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
            requestLogging(requestWrapper, logId);
            responseLogging(responseWrapper, startTime, logId);
            responseWrapper.copyBodyToResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestLogging(ContentCachingRequestWrapper request, UUID logId) {
        log.info(
                String.format(
                        "Log-ID: %s, IP: %s, URI: %s, Http-Method: %s, Params: %s, Content-Type: %s, User-Cookies: %s, User-Agent: %s, Request-Body: %s",
                        logId,
                        request.getRemoteAddr(),
                        request.getRequestURI(),
                        request.getMethod(),
                        request.getQueryString(),
                        request.getContentType(),
                        request.getCookies() != null ? String.join(", ", getCookiesAsString(request.getCookies())) : "[none]",
                        request.getHeader("User-Agent"),
                        getRequestBody(request.getContentAsByteArray())
                )
        );
    }

    private void responseLogging(ContentCachingResponseWrapper response, long startTime, UUID logId) {
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        log.info(
                String.format(
                        "Log-ID: %s, Status-Code: %d, Content-Type: %s, Response Time: %dms, Response-Body: %s",
                        logId,
                        response.getStatus(),
                        response.getContentType(),
                        responseTime,
                        new String(response.getContentAsByteArray(), StandardCharsets.UTF_8)
                )
        );
    }

    private String getRequestBody(byte[] byteArrayContent) {
        String oneLineContent = new String(byteArrayContent, StandardCharsets.UTF_8).replaceAll("\\s", "");
        return StringUtils.hasText(oneLineContent) ? oneLineContent : "[empty]";
    }

    private String[] getCookiesAsString(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .map(cookie -> String.format("%s=%s", cookie.getName(), cookie.getValue()))
                .toArray(String[]::new);
    }
}
