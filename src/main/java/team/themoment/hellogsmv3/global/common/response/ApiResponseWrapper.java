package team.themoment.hellogsmv3.global.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  org.springframework.http.MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof CommonApiMessageResponse<?>) {
            return byPassResponse(body, response);
        }


        if (body instanceof Map) {
            Map<String, Object> bodyMap = (Map<String, Object>) body;
            CommonApiMessageResponse<Object> errorResponse = exceptionResponse(response, bodyMap);
            if (errorResponse != null) return errorResponse;
        }

        CommonApiResponse<Object> commonApiResponse = new CommonApiResponse<>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "OK",
                body
        );

        response.setStatusCode(HttpStatus.OK);
        return commonApiResponse;
    }

    private static Object byPassResponse(Object body, ServerHttpResponse response) {
        CommonApiMessageResponse<?> commonApiMessageResponse = (CommonApiMessageResponse<?>) body;
        response.setStatusCode(commonApiMessageResponse.status());
        return body;
    }

    private static CommonApiMessageResponse<Object> exceptionResponse(ServerHttpResponse response, Map<String, Object> bodyMap) {
        if (bodyMap.containsKey("status")) {
            int statusCode = (int) bodyMap.get("status");
            if (statusCode >= 400 && statusCode < 600) {
                HttpStatus status = HttpStatus.valueOf(statusCode);
                CommonApiMessageResponse errorResponse = CommonApiMessageResponse.error(status.getReasonPhrase(), status);
                response.setStatusCode(HttpStatusCode.valueOf(statusCode));
                return errorResponse;
            }
        }
        return null;
    }
}
