package com.jelly.boot.controller;

import com.jelly.boot.message.output.Result;
import com.jelly.boot.request.DemoRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：zhang guo dong
 * @date ：Created in 2020/9/24 16:43
 * @description ：demo
 */
@RestController
public class DemoController {

    @GetMapping("/t")
    public Result test() {
        return Result.ok("ok,ok").build();
    }

    @PostMapping("/t")
    public Result testPost(@RequestBody @Validated DemoRequest demoRequest) {
        System.out.println(demoRequest);
        return Result.ok("ok,ok").data(demoRequest).build();
    }
}
