package com.suyi.dms.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suyi.dms.dto.LoginDTO;
import com.suyi.dms.pojo.User;
import com.suyi.dms.service.UserService;
import com.suyi.dms.utils.MD5Util;
import com.suyi.dms.utils.VerifyCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dms/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping("/authImage")
    @ResponseBody
    public void authImage(HttpServletResponse resp, HttpSession session) throws IOException {
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires",0);
        resp.setContentType("image/jpeg");
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        session.removeAttribute("verifyCode");
        session.setAttribute("verifyCode", verifyCode);
        int w=100;
        int h=30;
        VerifyCodeUtil.outputImage(w, h, resp.getOutputStream(), verifyCode);
    }

    @RequestMapping("/checkCaptcha")
    @ResponseBody
    public Map checkCaptcha(HttpSession session, String captcha){
        Boolean b = null;
        Map<String, Object> map = new HashMap<>();
        String verifyCode = (String) session.getAttribute("verifyCode");
        if (captcha.equalsIgnoreCase(verifyCode)){
            map.put("code", 1);
            map.put("message", "验证码正确！");
        }else {
            map.put("code", 0);
            map.put("message", "验证码错误！");
        }
        return map;
    }

    /**
     *
     * 功能描述: 登入系统
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/11/22 15:47
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, LoginDTO loginDTO, HttpSession session){
        logger.info("进行登陆");
        Map<String,Object> data = new HashMap();
        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();

        String username = loginDTO.getUsername().trim();
        String password = loginDTO.getPassword().trim();
        String rememberMe = loginDTO.getRememberMe();
        String host = request.getRemoteAddr();

        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,host);

        // 设置 remenmberMe 的功能
        if (rememberMe != null && rememberMe.equals("on")) {
            token.setRememberMe(true);
        }

        try {
            subject.login(token);
            // 登录成功
            User user = (User) subject.getPrincipal();

            session.setAttribute("user", user.getUsername());
            session.setAttribute("perm",user.getPerms());
            System.out.println(session.getAttribute("perm"));
            data.put("code",1);
            data.put("url","/home");
            data.put("message","登陆成功");
            logger.info(user.getUsername()+"登陆成功");
        } catch (UnknownAccountException e) {
            data.put("code",0);
            data.put("message",username+"账号不存在");
            logger.error(username+"账号不存在");
            return data;
        }catch (DisabledAccountException e){
            data.put("code",0);
            data.put("message",username+"账号异常");
            logger.error(username+"账号异常");
            return data;
        }
        catch (AuthenticationException e){
            data.put("code",0);
            data.put("message",username+"密码错误");
            logger.error(username+"密码错误");
            return data;
        }

        return data;
    }

    /**
     *
     * 功能描述: 修改密码
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/11/22 17:26
     */
    @RequestMapping("updatePwd")
    @ResponseBody
    public Map<String,Object> updatePwd(String password, String pwd, String isPwd){
        logger.info("进行密码重置");
        Map<String,Object> data = new HashMap();
        if(!pwd.equals(isPwd)){
            data.put("code",0);
            data.put("msg","两次输入的密码不一致!");
            logger.error("两次输入的密码不一致!");
        }
        //获取当前登陆的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername()).last("LIMIT 1");
        User one = userService.getOne(wrapper);
        if (!one.getPassword().equals(MD5Util.getMD5(user.getUsername(),password))){
            data.put("code", 0);
            data.put("msg", "输入的原密码不正确！");
            logger.error("输入的原密码不正确！");
        }else {
            user.setPassword(MD5Util.getMD5(user.getUsername(),pwd));
            boolean result = userService.update(user, wrapper);
            if(result){
                data.put("code",1);
                data.put("msg","修改密码成功！");
                logger.info("用户修改密码成功！");
            }else {
                data.put("code",0);
                data.put("msg","修改密码失败！");
                logger.error("用户修改密码失败！");
            }
        }
        return data;
    }

}
