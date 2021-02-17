package com.yqy.logserver.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bahsk
 * @createTime 2021-02-14 0:07
 * @description
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("/hello3")
    public String sayHelloAgain(){
        return "Hello2";
    }


    @GetMapping("/hello2")
    public String sayHelloB(){
        return "Hello28";
    }

    @GetMapping("/hello32")
    public String sayHelloA(){
        return "Hello30";
    }





}
