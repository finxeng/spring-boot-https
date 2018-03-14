package com.lovejobs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/https",method = RequestMethod.GET)
    @ResponseBody
    public String send() {
        return "hello " + new Date();
    }
}
