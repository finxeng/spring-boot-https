package com.lovejobs.controller;

import com.lovejobs.config.SQSMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    SQSMQSender sqsmqSender;

    @RequestMapping(value = "/send",method = RequestMethod.GET)
    @ResponseBody
    public String send() {
        try {
            sqsmqSender.send("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
