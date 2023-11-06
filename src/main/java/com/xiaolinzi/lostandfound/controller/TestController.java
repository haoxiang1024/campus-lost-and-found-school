package com.xiaolinzi.lostandfound.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${img.address}")
    String basePath;

    /**
     * 图片上传测试
     * @return
     */
    @PostMapping("/unload")
    public String userUpdateSelf(MultipartFile file){

//        String basePath = "/usr/java/project/final/images/";
//        String basePath = "D:\\images";
        //原始文件名
        String originalFilename = file.getOriginalFilename();//xxx.png
        //对原始名进行截取"."后面的字符
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//.png
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在：目录不存在，需要创建
        if(!dir.exists()) dir.mkdirs();
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @GetMapping("/loadimg")
    public void getImg2(HttpServletResponse response) {
        File file = new File("/usr/java/project/final/images/91845ecc-db8f-4f8a-9b40-bc1e21d8aa85.png");
        //imgPath为服务器图片地址
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
    @GetMapping("/loadimg/{imgaddress}")
    public void getImg2(HttpServletResponse response,@PathVariable("imgaddress") String imgaddress) {
        File file = new File("D:\\images\\"+imgaddress);
        //imgPath为服务器图片地址
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
}
