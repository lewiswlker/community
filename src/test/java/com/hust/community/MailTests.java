package com.hust.community;

import com.hust.community.utils.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * @author Liubo Ren
 * @version 1.0
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("rliubo@foxmail.com", "Test", "hello world!");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "Liubo");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("lewiswlker_007@126.com", "HTML", content);
    }

}
