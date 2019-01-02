package com.foxwho.demo.javaspringbootmailfreemarkerdemo.service;

import java.util.Map;

public interface MailService {

    void sendMail(Map<String, Object> params, String title, String templateName);
}
