package com.hust.community.controller;

import com.hust.community.annotation.LoginRequired;
import com.hust.community.entity.User;
import com.hust.community.service.FollowService;
import com.hust.community.service.LikeService;
import com.hust.community.service.UserService;
import com.hust.community.utils.CommunityConstant;
import com.hust.community.utils.CommunityUtil;
import com.hust.community.utils.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "请您选择一张图片");
            return "site/setting";
        }

        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式不正确");
            return "site/setting";
        }

        String newFileName = CommunityUtil.generateUUID() + suffix;
        File filePath = new File(uploadPath + "/" + newFileName);
        try {
            headerImage.transferTo(filePath);
        } catch (IOException e) {
            logger.error("上传图片失败" + e.getMessage());
            throw new RuntimeException("上传文件失败", e);
        }

        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + newFileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图像
        response.setContentType("image/" + suffix);
        try (
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("从服务器读取图像失败" + e.getMessage());
            throw new RuntimeException("从服务器读取图像失败", e);
        }

    }

    @LoginRequired
    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword, String confirmPassword, @CookieValue("ticket") String ticket, Model model) {
        if (oldPassword == null) {
            model.addAttribute("oldPasswordError", "原密码不能为空");
            return "site/setting";
        }
        if (newPassword == null) {
            model.addAttribute("newPasswordError", "新密码不能为空");
            return "site/setting";
        }
        if (confirmPassword == null) {
            model.addAttribute("confirmPasswordError", "两次输入的密码不一致");
            return "site/setting";
        } else if (!confirmPassword.equals(newPassword)) {
            model.addAttribute("confirmPasswordError", "两次输入的密码不一致");
            return "site/setting";
        }
        User user = hostHolder.getUser();
        oldPassword = CommunityUtil.md5(oldPassword + user.getSalt());
        if (oldPassword.equals(user.getPassword())) {
            userService.updatePassword(user.getId(), newPassword);
            userService.logout(ticket);
            return "redirect:/login";
        } else {
            model.addAttribute("oldPasswordError", "原始密码错误");
            return "site/setting";
        }
    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        model.addAttribute("user", user);
        // 点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        // 关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);

        // 粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);

        // 是否已经关注
        boolean hasFollowed = false;
        if(hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "site/profile";
    }
}
