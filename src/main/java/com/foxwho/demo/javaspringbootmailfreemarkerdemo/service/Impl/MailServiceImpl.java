package com.foxwho.demo.javaspringbootmailfreemarkerdemo.service.Impl;

import com.foxwho.demo.javaspringbootmailfreemarkerdemo.service.MailService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    /**
     * 发送人
     */
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void sendMail(Map<String, Object> params, String title, String templateName) {
        try {
            //邮件标题
            String subject = title + "-" + LocalDate.now();
            //发送给谁,这里直接使用 邮件服务器的发送方，你可以改成你想要接受邮件的那个人
            InternetAddress[] internetAddresses = InternetAddress.parse(from);
            //InternetAddress[] internetAddresses = InternetAddress.parse("XXXXXX@foxwho.com");

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(internetAddresses);
            helper.setSubject(subject);

            try {
                Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
                try {
                    log.info("Template={}",template);
                    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
                    log.info("FreeMarkerTemplateUtils TEXT={}",text);
                    helper.setText(text, true);
                    log.info("helper ={}", helper);
                    javaMailSender.send(mimeMessage);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
