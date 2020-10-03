package ${groupId};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangguodong
 */
@SpringBootApplication
@MapperScan("${groupId}.dao")
public class ScaffoldApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScaffoldApplication.class, args);
    }
}
