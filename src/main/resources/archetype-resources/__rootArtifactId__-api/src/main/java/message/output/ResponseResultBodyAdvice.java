package ${groupId}.message.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * 对controller中的返回 response 数据封装为统一的Result。在controller的方法或者类上 增加 @IgnoreResponseResult 时 不会生效。
 *
 * @author ：zhang guo dong
 */
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger(ResponseResultBodyAdvice.class);

    private static final Class<? extends Annotation> IGNORE_ANNOTATION_TYPE = IgnoreResponseResult.class;
    /**
     * 需要忽略的地址
     */
    private static String[] ignores = new String[]{
            "/swagger-resources",
            "/v2/api-docs"
    };

    /**
     * 判断类或者方法是否使用了 @ResponseResultBody
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 当类或者方法使用了 @ResponseResultBody 就会调用这个方法
     */
    @Override
    public Object beforeBodyWrite(Object div, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (this.ignore(request.getURI().toString())) {
            return div;
        }

        // 添加忽略返回result的注解
        if (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), IGNORE_ANNOTATION_TYPE) ||
                returnType.hasMethodAnnotation(IGNORE_ANNOTATION_TYPE)) {
            return div;
        }

        // 防止重复包裹的问题出现
        if (div instanceof Result) {
            return div;
        }

        return Result.ok(div).build();
    }

    /**
     * 判断url是否需要拦截
     */
    private boolean ignore(String uri) {
        for (String string : ignores) {
            if (uri.contains(string)) {
                return true;
            }
        }
        return false;
    }
}