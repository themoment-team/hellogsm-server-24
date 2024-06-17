package team.themoment.hellogsmv3.global.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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

        if (body instanceof CommonApiResponse) {
            CommonApiResponse<?> commonApiResponse = (CommonApiResponse<?>) body;
            response.setStatusCode(commonApiResponse.status());
            return body;
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
}
