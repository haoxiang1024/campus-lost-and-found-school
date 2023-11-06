package com.xiaolinzi.lostandfound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/getPage")
public class PageController {
    /**
     * 获取后端的登录界面
     * @return
     */
    @RequestMapping({"/login","/",""})
    public String goLogin(){
        return "admin/login";
    }

    /**
     * 获取后端的首页
     * @return
     */
    @RequestMapping("/index")
    public String goIndex(){
        return "admin/index";
    }

    @RequestMapping("/test")
    public String goTest(){
        return "admin/test";
    }

    @RequestMapping("/userList")
    public String goUserList(){
        return "admin/userList";
    }

    @RequestMapping("/foundList")
    public String goFoundList(){
        return "admin/foundList";
    }

    @RequestMapping("/lostList")
    public String goLostList(){
        return "admin/lostList";
    }

    @RequestMapping("/typeList")
    public String goTypeList(){
        return "admin/typeList";
    }

    @RequestMapping("/home")
    public String goHome(){
        return "admin/home";
    }
}
