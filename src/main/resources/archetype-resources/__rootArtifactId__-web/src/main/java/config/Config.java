package ${groupId}.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import ${groupId}.message.input.Jackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author ：zhang guo dong
 */
@Configuration
public class Config {

    @Value("${dollar}{request.body.params.enable:true}")
    private boolean paramsEnable;

    /**
     * 注册message-converter
     * 自动转换驼峰为snake形式
     */
    @Bean
    public HttpMessageConverters jackson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        MappingJackson2HttpMessageConverter converter = new Jackson2HttpMessageConverter(paramsEnable, objectMapper);
        return new HttpMessageConverters(converter);
    }
}
