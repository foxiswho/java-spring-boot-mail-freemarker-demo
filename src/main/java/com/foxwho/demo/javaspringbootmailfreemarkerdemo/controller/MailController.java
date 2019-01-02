package com.foxwho.demo.javaspringbootmailfreemarkerdemo.controller;


import com.foxwho.demo.javaspringbootmailfreemarkerdemo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    public String index(){
        Map<String, Object> params = new HashMap<>();
        params.put("username","这是用户名");
        params.put("resetPwdUrl","http://foxwho.com");
        mailService.sendMail(params,"这是测试标题","sendRestLoginPwdTemplate.ftl");

        return "ok";
    }
}
