package ${groupId}.message.input;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ${groupId}.error.ErrorStatus;
import ${groupId}.error.ServerException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ：zhang guo dong
 */
public class Jackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final Class<? extends Annotation> IGNORE_ANNOTATION_TYPE = IgnoreRequestInput.class;

    private boolean isRequestInput;

    public Jackson2HttpMessageConverter(boolean isRequestInput, ObjectMapper objectMapper) {
        super(objectMapper);
        this.isRequestInput = isRequestInput;
    }

    @Override
    public Object read(Type type, Class<?> aClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        // response 和不做特殊处理
        if (inputMessage instanceof ClientHttpResponse) {
            return super.read(type, aClass, inputMessage);
        }
        // 已经包装的
        if ((type instanceof ParameterizedType) && ((ParameterizedType) type).getRawType() == Input.class) {
            return super.read(type, aClass, inputMessage);
        }

        // 需要跳过
        if (aClass.isAnnotationPresent(IGNORE_ANNOTATION_TYPE) || aClass.isAnnotationPresent(IGNORE_ANNOTATION_TYPE)) {
            return super.read(type, aClass, inputMessage);
        }

        if (isRequestInput) {
            JavaType javaType = TypeFactory.defaultInstance().constructParametricType(Input.class,
                    TypeFactory.defaultInstance().constructType(type));

            Input inputMessageObj = getObjectMapper().readValue(inputMessage.getBody(), javaType);

            if (inputMessageObj == null || inputMessageObj.getParams() == null) {
                throw new ServerException(ErrorStatus.ILLEGAL_ARGUMENT, "params", " not exist!");
            }

            return inputMessageObj.getParams();
        }

        return super.read(type, aClass, inputMessage);
    }
}
