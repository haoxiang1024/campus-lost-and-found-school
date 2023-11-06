package com.xiaolinzi.lostandfound.controller;

import com.xiaolinzi.lostandfound.model.Found;
import com.xiaolinzi.lostandfound.model.LostFoundType;
import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.model.User;
import com.xiaolinzi.lostandfound.service.FoundService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/found")
public class FoundController {

    @Autowired
    private FoundService foundService;

    /**
     * 信息里面图片的路径
     */
    @Value("${images.address}")
    String imagesAddress;

    /**
     * 获取所有置顶的信息
     * @return
     */
    @GetMapping("/stick")
    public Result getStickFound(){
        List<Found> foundList = foundService.getStickFound();
        Integer code = foundList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundList.size() != 0 ? "查找成功，结果如下：" : "查询失败，无置顶的招领信息。";
        return new Result(code,foundList,msg);
    }

    /**
     * 用户发布招领信息
     * @param found （title，content，place，phone）
     * @param typeID 类别的编号
     * @param userID 发布人的编号
     * @param imgFile 上传的图片
     * @return
     */
    @PostMapping("/addFound")
    public Result addFound(Found found,@RequestParam("typeID") int typeID,
            @RequestParam("userID") int userID,@RequestParam("imgFile") MultipartFile imgFile){
        LostFoundType lostFoundType = new LostFoundType();
        lostFoundType.setId(typeID);
        found.setLostFoundType(lostFoundType);
        User user = new User();
        user.setId(userID);
        found.setUser(user);
        if (imgFile.getOriginalFilename().equals("")){
            found.setImg("default.jpg");
        }else {
            found.setImg(uploadImage(imgFile));
        }
        boolean flag = foundService.addFound(found);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    /**
     * 修改招领信息的置顶状态
     * @param foundID
     * @param stick
     * @return
     */
    @PutMapping("/updateStick")
    public Result updateFoundStick(@RequestParam("foundID") int foundID, @RequestParam("stick") int stick){
        if (stick == 1){
            List<Found> foundList = foundService.getStickFound();
            if (foundList.size()>=10){
                return new Result(Code.UPDATE_ERR,false);
            }
        }
        boolean flag = foundService.updateStick(foundID,stick);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 根据招领信息编号增加点击量，并获取同类型的信息
     * @param foundID 招领信息编号
     * @param typeID 分类编号
     * @return
     */
    @PostMapping("/getSimilarFound")
    public Result getSimilarFound(@RequestParam("foundID") int foundID,@RequestParam("typeID")int typeID){
        List<Found> foundList = foundService.getSimilarFound(foundID,typeID);
        Integer code = foundList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundList.size() != 0 ? "查找成功，结果如下：" : "查询失败，无置顶的招领信息。";
        return new Result(code,foundList,msg);
    }

    /**
     * 用户修改招领信息
     * @param found
     * @return
     */
    @PutMapping("/userUpdateFound")
    public Result userUpdateFound(@RequestBody Found found){
//        try {
//
//        }catch(Exception e) {
//            throw new BusinessException(Code.BUSINESS_ERR,"请不要使用你的技术挑战我的耐性!");
//        }
        boolean flag = foundService.userUpdateFound(found);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 用户删除个人发布的招领信息
     * @param foundID 招领信息编号
     * @return
     */
    @DeleteMapping("/userDeleteFound")
    public Result userDeleteFound(@RequestParam("foundID") int foundID){
        boolean flag = foundService.userDeleteFound(foundID);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }

    /**
     * 删除招领信息
     * @param foundID 招领信息编号
     * @return
     */
    @DeleteMapping("/deleteFound/{foundID}")
    public Result deleteFound(@PathVariable Integer foundID){
        boolean flag = foundService.userDeleteFound(foundID);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }
//
    /**
     * 管理员删除招领信息（可批量删除）
     * @param foundIDs 招领信息编号的数组
     * @return
     */
    @DeleteMapping("/adminDeleteFound")
    public Result adminDeleteFound(@RequestParam("foundIDs[]")int[] foundIDs){
        boolean flag = foundService.adminDeleteFound(foundIDs);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }


    /**
     *查询未找到的所有分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @return
     */
    @PostMapping("/noFind")
    public Result noFind(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Found> foundPageBean = foundService.selectByPage(currentPage,pageSize,"未找到");
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，无未找到的信息。";
        return new Result(code,foundPageBean,msg);
    }
    /**
     *查询已找到的所有分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @return
     */
    @PostMapping("/find")
    public Result find(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Found> foundPageBean = foundService.selectByPage(currentPage,pageSize,"已找到");
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，无已找到的信息。";
        return new Result(code,foundPageBean,msg);
    }
    /**
     *查询所有的分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @return
     */
    @PostMapping("/all")
    public Result all(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Found> foundPageBean = foundService.selectByPage(currentPage,pageSize,null);
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，没有招领信息。";
        return new Result(code,foundPageBean,msg);
    }
    /**
     *分类查询未找到的所有分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @param typeID 分类编号
     * @return
     */
    @PostMapping("/noFindAndType")
    public Result noFindAndType(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize, @RequestParam("typeID") int typeID) {
        PageBean<Found> foundPageBean = foundService.selectByPageAndType(currentPage,pageSize,typeID,"未找到");
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该分类下无未找到的信息。";
        return new Result(code,foundPageBean,msg);
    }
    /**
     *分类查询已找到的所有分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @param typeID 分类编号
     * @return
     */
    @PostMapping("/findAndType")
    public Result findAndType(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize, @RequestParam("typeID") int typeID) {
        PageBean<Found> foundPageBean = foundService.selectByPageAndType(currentPage,pageSize,typeID,"已找到");
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该分类下无已找到的信息。";
        return new Result(code,foundPageBean,msg);
    }
    /**
     *分类查询所有的分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @param typeID 分类编号
     * @return
     */
    @PostMapping("/allAndType")
    public Result allAndType(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize, @RequestParam("typeID") int typeID) {
        PageBean<Found> foundPageBean = foundService.selectByPageAndType(currentPage,pageSize,typeID,null);
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该分类下没有招领信息。";
        return new Result(code,foundPageBean,msg);
    }

    /**
     *不根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param condition 招领信息的状态
     * @return
     */
    @PostMapping("/getSelfFound")
    public Result getSelfFound(@RequestParam("userID") int userID,@RequestParam("condition") String condition) {
        List<Found> foundList = foundService.getSelfFound(userID,condition);
        Integer code = foundList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundList.size() != 0 ? "查找成功。" : "查询失败，没有招领信息。";
        return new Result(code,foundList,msg);
    }

    /**
     *根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param typeID 分类编号
     * @param condition 招领信息的状态
     * @return
     */
    @PostMapping("/getSelfFoundAndType")
    public Result getSelfFoundAndType(@RequestParam("userID") int userID, @RequestParam("typeID") int typeID,@RequestParam("condition") String condition) {
        List<Found> foundList = foundService.getSelfFoundAndType(userID,typeID,condition);
        Integer code = foundList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundList.size() != 0 ? "查找成功。" : "查询失败，没有招领信息。";
        return new Result(code,foundList,msg);
    }

    /**
     * 查询信息
     * @param searchTitle 标题
     * @param searchPlace 地点
     * @param typeID 分类
     * @param condition 状态
     * @param currentPage 页码
     * @param pageSize 一页多少条
     * @return
     */
    @PostMapping("/searchDate")
    public Result getDateBySearch(@RequestParam("searchTitle") String searchTitle,@RequestParam("searchPlace") String searchPlace,@RequestParam("typeID") String typeID
            ,@RequestParam("condition") String condition,@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Found> foundPageBean = foundService.getDateBySearch(searchTitle,searchPlace,typeID,condition,currentPage,pageSize);
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无招领信息。";
        return new Result(code,foundPageBean,msg);
    }

    /**
     * 查询信息
     * @param searchTitle 标题
     * @param searchPlace 地点
     * @param typeID 分类
     * @param stick 是否置顶(1:置顶，0不置顶)
     * @param sort 排序(ascending:升序，descending:降序，还有null。)
     * @param condition 状态
     * @param currentPage 页码
     * @param pageSize 一页多少条
     * @return
     */
    @PostMapping("/getDate")
    public Result getDate(@RequestParam("searchTitle") String searchTitle,@RequestParam("searchPlace") String searchPlace,
            @RequestParam("typeID") String typeID,@RequestParam("stick") int stick,@RequestParam("sort") String sort
            ,@RequestParam("condition") String condition,@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Found> foundPageBean = foundService.getDate(searchTitle,searchPlace,typeID,stick,sort,condition,currentPage,pageSize);
        Integer code = foundPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = foundPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无招领信息。";
        return new Result(code,foundPageBean,msg);
    }

    /**
     * 修改招领信息状态为已找到
     * @param foundID
     * @return
     */
    @PutMapping("/updateStateForTrue/{foundID}")
    public Result updateStateForTrue(@PathVariable int foundID){
        boolean flag = foundService.updateStateForTrue(foundID);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }


    /**
     * 加载信息里面的图片
     * @param response
     * @param img
     */
    @GetMapping("/loadImages/{img}")
    public void loadPhoto(HttpServletResponse response, @PathVariable("img") String img) {
        File file = new File(imagesAddress+img);
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
     * 上传图片信息到指定的目录下面里面
     * @param imgFile
     * @return
     */
    public String uploadImage(MultipartFile imgFile){
        String originalFilename = imgFile.getOriginalFilename();//xxx.png
        //对原始名进行截取"."后面的字符
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//.png
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建一个目录对象
        File dir = new File(imagesAddress);
        //判断当前目录是否存在：目录不存在，需要创建
        if(!dir.exists()) dir.mkdirs();
        try {
            //将临时文件转存到指定位置
//            image.transferTo(new File(imageAddress + fileName));
            FileUtils.copyInputStreamToFile(imgFile.getInputStream(),new File(imagesAddress + fileName));
//            导入的io包

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
