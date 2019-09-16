package com.zym.submit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zym
 * @date 2019-09-06-20:04
 */
@Controller             //Controller, RestController的共同点都是用来表示spring某个类的是否可以接收HTTP请求
                        //但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
public class HelloController {


    @GetMapping("/hello")
    public String hello(@RequestParam(name ="name")String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
