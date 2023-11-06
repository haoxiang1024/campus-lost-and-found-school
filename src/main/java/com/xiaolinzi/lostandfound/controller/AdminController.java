package com.xiaolinzi.lostandfound.controller;

import com.xiaolinzi.lostandfound.model.Admin;
import com.xiaolinzi.lostandfound.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Value("${photo.address}")
    private String photoAddress;


    /**
     * 用户登录，account可以是账号也可以是联系方式
     * @param account
     * @param password
     * @return
     */
    @PostMapping
    public Result login(@RequestParam("account") String account,@RequestParam("password") String password) {
//        System.out.println(account+"你好"+password);
        Admin admin = adminService.login(account,password);
        Integer code = admin != null ? Code.GET_OK : Code.GET_ERR;
        String msg = admin != null ? "登录成功！" : "登录失败，账号或密码错误。";
        return new Result(code,admin,msg);
    }

    /**
     * 管理员修改个人信息
     * @param admin
     * @return
     */
    @PutMapping("/adminUpdateSelf")
    public Result adminUpdateSelf(@RequestBody Admin admin){
        boolean flag = adminService.adminUpdateSelf(admin);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 用户修改个人密码
     * @param admin
     * @return
     */
    @PutMapping("/adminUpdatePassword")
    public Result adminUpdatePassword(@RequestBody Admin admin){
        boolean flag = adminService.adminUpdatePassword(admin);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 管理员修改个人的头像
     * @param adminID 用户ID
     * @param photo 修改的新头像文件名
     * @return
     */
    @PutMapping("/adminUpdatePhoto")
    public Result adminUpdatePhoto(@RequestParam("adminID") int adminID, @RequestParam("photo") MultipartFile photo){
        String originalFilename = photo.getOriginalFilename();//xxx.png
        //对原始名进行截取"."后面的字符
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//.png
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建一个目录对象
        File dir = new File(photoAddress);
        //判断当前目录是否存在：目录不存在，需要创建
        if(!dir.exists()) dir.mkdirs();
        try {
            //将临时文件转存到指定位置
            photo.transferTo(new File(photoAddress + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Admin admin = null;
        boolean flag = adminService.adminUpdatePhoto(adminID,fileName);
        if (flag){
            admin = adminService.getAdminByID(adminID);
        }
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,admin);
    }
}
