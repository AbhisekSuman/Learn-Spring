package com.kodnest.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class Future {

    @RequestMapping("/user/{future}")
    @ResponseBody
    public int displayFuture(@PathVariable int future) {
        int res = future + 10;
        return res;
    }
}
