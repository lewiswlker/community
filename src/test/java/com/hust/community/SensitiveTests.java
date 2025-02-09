package com.hust.community;

import com.hust.community.utils.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里可以*赌*博☆，可以▷吸☆毒☆，快来玩！";
        String filter = sensitiveFilter.filter(text);
        System.out.println(filter);
    }
}
