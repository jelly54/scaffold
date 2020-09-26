package com.jelly.boot.controller;

import com.jelly.boot.error.ServerException;
import com.jelly.boot.message.input.IgnoreRequestInput;
import com.jelly.boot.message.output.IgnoreResponseResult;
import com.jelly.boot.message.output.Result;
import com.jelly.boot.request.DemoRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author ：zhang guo dong
 */
@RestController
public class DemoController {
    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
        INFO.put("deptName", "部门名称");
    }

    @PostMapping("bind")
    public Result dataBind(@RequestBody @Validated DemoRequest request) {

        return Result.ok(request).build();
    }

    @GetMapping("hello")
    public HashMap<String, Object> hello() {
        return INFO;
    }

    @IgnoreRequestInput
    @IgnoreResponseResult
    @GetMapping("hello1")
    public HashMap<String, Object> hello1() {
        return INFO;
    }

    /**
     * 测试重复包裹
     */
    @GetMapping("result")
    public Result helloResult() {
        return Result.ok(INFO).build();
    }

    @GetMapping("helloError")
    public HashMap<String, Object> helloError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("helloMyError")
    public HashMap<String, Object> helloMyError() throws Exception {
        throw new ServerException();
    }
}
