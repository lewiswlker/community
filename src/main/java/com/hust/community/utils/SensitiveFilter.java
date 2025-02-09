package com.hust.community.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换符
    private static final String REPLACEMENT = "***";

    // 根节点
    private TrieNode rootNode = new TrieNode();

    /**
     * 初始化前缀树
     */
    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                addKeyword(keyword);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 将敏感词添加到前缀树
     */
    public void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            tempNode = subNode;
            // 设置结束标志
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     *
     * @param text (过滤前文本)
     * @return 过滤后文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }

        TrieNode tempNode = rootNode; // 1
        int begin = 0; // 2
        int position = 0; // 3
        StringBuilder sb = new StringBuilder();
        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号
            if (isSymbol(c)) {
                // 如果c是特殊字符，并且tempNode处于根节点，说明不处于疑似状态，可以begin也跳过这个字符
                if(tempNode==rootNode) {
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // c不是敏感字,begin嫌疑排除
                sb.append(text.charAt(begin));
                position = ++begin;
                tempNode = rootNode;
            } else if(tempNode.isKeywordEnd()) {
                // 发现敏感词
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            } else {
                // 检查下个字符,继续检测
                position++;
            }
        }
        // 如果最后疑似但是没确认就走到头了，就遗漏了部分字符，这里补上
        sb.append(text.substring(begin));
        return sb.toString();
    }

    /**
     * 若是特殊符号，则返回true，正常文字则返回false，用于判断是否是符号
     * @param c
     * @return
     */
    private boolean isSymbol(Character c) {
        // c < 0x2E80 || c > 0x9FFF 东亚文字
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // 前缀树
    private class TrieNode {
        // 关键词结束的标识
        private boolean isKeywordEnd = false;

        // 子节点，key是下级字符，value是对应的节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }
}
