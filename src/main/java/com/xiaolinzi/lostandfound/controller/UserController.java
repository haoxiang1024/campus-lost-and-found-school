package com.xiaolinzi.lostandfound.controller;

import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.model.User;
import com.xiaolinzi.lostandfound.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${img.address}")
    private String imgAddress;
    @Value("${photo.address}")
    private String photoAddress;
    @Value("${images.address}")
    private String imagesAddress;
    /**
     * 用户登录，account可以是账号也可以是联系方式
     * @param account
     * @param password
     * @return
     */
    @PostMapping
    public Result login(@RequestParam("account") String account,@RequestParam("password") String password) {
//        System.out.println(account+"你好"+password);
        User user = userService.login(account,password);
        Integer code = user != null ? Code.GET_OK : Code.GET_ERR;
        String msg = user != null ? "登录成功！" : "登录失败，用户不存在。";
        return new Result(code,user,msg);
    }

//    /**
//     * 用户登录，account可以是账号也可以是联系方式
//     * @param userID 用户ID
//     * @return
//     */
//    @PostMapping("getUser")
//    public Result getUser(@RequestParam("userID") int userID) {
////        System.out.println(account+"你好"+password);
//        User user = userService.getUserByID(userID);
//        Integer code = user != null ? Code.GET_OK : Code.GET_ERR;
//        String msg = user != null ? "登录成功！" : "登录失败，用户不存在。";
//        return new Result(code,user,msg);
//    }

    /**
     * 根据对应的条件获取对应的信息
     * @param user （account，nickname，phone）
     * @param state 用户的状态（-1：所有，0：禁用，1：启用）
     * @param currentPage 页码
     * @param pageSize 每页的条数
     * @return
     */
    @PostMapping("/searchDate")
    public Result getDateBySearch(User user,@RequestParam("state") int state,@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
//        System.out.println(user);
        PageBean<User> userPageBean = userService.getDateBySearch(user,state,currentPage,pageSize);
        Integer code = userPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = userPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无用户信息。";
//        System.out.println(userPageBean);
        return new Result(code,userPageBean,msg);
    }

//    @PostMapping("/searchDate")
//    public Result getDateBySearch(User user,int state, int currentPage, int pageSize) {
//        System.out.println(user);
//        PageBean<User> userPageBean = userService.getDateBySearch(user,state,currentPage,pageSize);
//        Integer code = userPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
//        String msg = userPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无用户信息。";
//        return new Result(code,userPageBean,msg);
//    }

    /**
     * 用户修改个人信息
     * @param user
     * @return
     */
    @PutMapping("/userUpdateSelf")
    public Result userUpdateSelf(@RequestBody User user){
        boolean check = userService.selectPhone(user.getId() ,user.getPhone());
        if (check){
            return new Result(Code.UPDATE_ERR, false,"联系方式已存在，无法修改。");
        }
        boolean flag = userService.userUpdateSelf(user);
        User user1 = null;
        if (flag){
            user1 = userService.getUserByID(user.getId());
        }
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,user1);
    }

    /**
     * 管理员修改用户信息
     * @param user (id,password,nickname,phone,sex,photo)
     * @param newPhoto 新上传的修改的头像
     * @return
     */
    @PutMapping("/adminUpdateUser")
    public Result adminUpdateUser(User user,@RequestParam("newPhoto") MultipartFile newPhoto){
        boolean check = userService.selectPhone(user.getId() ,user.getPhone());
        if (check){
            return new Result(Code.UPDATE_ERR, false,"联系方式已存在，无法修改。");
        }
        if (!newPhoto.getOriginalFilename().equals("")){
            user.setPhoto(uploadPhoto(newPhoto));
        }
        boolean flag = userService.adminUpdateUser(user);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 用户充值个人账户余额
     * @param userID 用户ID
     * @param balance   充值数额
     * @return
     */
    @PutMapping("/rechargeBalance")
    public Result rechargeBalance(@RequestParam("userID") int userID,@RequestParam("balance") BigDecimal balance){
        boolean flag = userService.rechargeBalance(userID,balance);
        User user = null;
        if (flag){
            user = userService.getUserByID(userID);
        }
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,user);
    }

    /**
     * 设置用户账号的禁用与启用
     * @param userID 用户ID
     * @param state 用户的状态（0：禁用，1：启用）
     * @return
     */
    @PutMapping("/setUpForbidden")
    public Result setUpForbidden(@RequestParam("userID") int userID,@RequestParam("state") int state){
        boolean flag = userService.setUpForbidden(userID,state);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 用户修改个人密码
     * @param userID 用户ID
     * @param password 修改的新密码
     * @return
     */
    @PutMapping("/userUpdatePassword")
    public Result userUpdatePassword(@RequestParam("userID") int userID,@RequestParam("password") String password){
        boolean flag = userService.userUpdatePassword(userID,password);
        User user = null;
        if (flag){
            user = userService.getUserByID(userID);
        }
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,user);
    }

    /**
     * 用户修改个人的头像
     * @param userID 用户ID
     * @param photo 修改的新头像文件名
     * @return
     */
    @PutMapping("/userUpdatePhoto")
    public Result userUpdatePhoto(@RequestParam("userID") int userID, @RequestParam("photo") MultipartFile photo){
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
        User user = null;
        boolean flag = userService.userUpdatePhoto(userID,fileName);
        if (flag){
            user = userService.getUserByID(userID);
        }
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,user);
    }

    /**
     * 用户进行个人注册
     * @param user
     * @return
     */
//    userRegister
    @PostMapping("/userRegister")
    public Result userRegister(@RequestBody User user) {
        System.out.println(user);
//        System.out.println(account+"你好"+password);
        boolean check = userService.selectAccount(user.getAccount(),user.getPhone());
        if (check){
            return new Result(Code.SAVE_ERR, false,"账号或联系方式已存在，无法注册。");
        }
        boolean flag = userService.userRegister(user);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }



    /**
     * 加载用户的个人头像
     * @param response
     * @param img
     */
    @GetMapping("/loadPhoto/{img}")
    public void loadPhoto(HttpServletResponse response, @PathVariable("img") String img) {
        File file = new File(photoAddress+img);
        if(file.exists() && file.isFile()){
            FileInputStream fis = null;
            OutputStream os = null;
            try {
                fis = new FileInputStream(file);
                os = response.getOutputStream();
                int count = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                    os.flush();//刷新流
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 上传头像到resource里面
     * @param photo
     * @return
     */
    public String uploadPhoto(MultipartFile photo){
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
//            image.transferTo(new File(imageAddress + fileName));
            FileUtils.copyInputStreamToFile(photo.getInputStream(),new File(photoAddress + fileName));
//            导入的io包
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @GetMapping
    public String test() {
        return new String("fjaopjfop");
    }
}
