package ${groupId}.controller;

import ${groupId}.error.ServerException;
import ${groupId}.message.input.IgnoreRequestInput;
import ${groupId}.message.output.IgnoreResponseResult;
import ${groupId}.message.output.Result;
import ${groupId}.request.DemoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author ：zhang guo dong
 */
@Api(description = "通用返回类，出参/入参封装 测试接口")
@RestController
public class OutPutTestController {
    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
        INFO.put("deptName", "部门名称");
    }

    @ApiOperation("手动绑定通用结果Result，对入参使用params封装")
    @PostMapping("bind")
    public Result dataBind(@RequestBody @Validated DemoRequest request) {

        return Result.ok(request).build();
    }

    @ApiOperation("返回HashMap，自动绑定通用结果Result")
    @GetMapping("hello")
    public HashMap<String, Object> hello() {
        return INFO;
    }

    @ApiOperation("返回HashMap，使用注解不对出入参封装")
    @IgnoreRequestInput
    @IgnoreResponseResult
    @GetMapping("hello1")
    public HashMap<String, Object> hello1() {
        return INFO;
    }

    @ApiOperation("测试重复包裹")
    @GetMapping("result")
    public Result helloResult() {
        return Result.ok(INFO).build();
    }

    @ApiOperation("统一异常捕获，Exception")
    @GetMapping("helloError")
    public HashMap<String, Object> helloError() throws Exception {
        throw new Exception("helloError");
    }

    @ApiOperation("统一异常捕获，自定义异常ServerException")
    @GetMapping("helloMyError")
    public HashMap<String, Object> helloMyError() throws Exception {
        throw new ServerException();
    }
}
