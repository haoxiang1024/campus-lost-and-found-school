package com.xiaolinzi.lostandfound.controller;

import com.xiaolinzi.lostandfound.model.*;
import com.xiaolinzi.lostandfound.service.LostService;
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
@RequestMapping("/lost")
public class LostController {

    @Autowired
    private LostService lostService;

    /**
     * 信息里面图片的路径
     */
    @Value("${images.address}")
    String imagesAddress;

    @GetMapping("/stick")
    public Result getStickLost(){
        List<Lost> lostList = lostService.getStickLost();
        Integer code = lostList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostList.size() != 0 ? "查找成功。" : "查询失败，无置顶的失物信息。";
        return new Result(code,lostList,msg);
    }

    /**
     * 修改失物信息的置顶状态
     * @param lostID
     * @param stick
     * @return
     */
    @PutMapping("/updateStick")
    public Result updateLostStick(@RequestParam("lostID") int lostID, @RequestParam("stick") int stick){
        if (stick == 1){
            List<Lost> lostList = lostService.getStickLost();
            if (lostList.size()>=10){
                return new Result(Code.UPDATE_ERR,false);
            }
        }
        boolean flag = lostService.updateStick(lostID,stick);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 用户发布失物信息
     * @param lost （title，content，place，phone）
     * @param typeID 类别的编号
     * @param userID 发布人的编号
     * @param imgFile 上传的图片
     * @return
     */
    @PostMapping("/addLost")
    public Result addLost(Lost lost,@RequestParam("typeID") int typeID,
                           @RequestParam("userID") int userID,@RequestParam("imgFile") MultipartFile imgFile){
        LostFoundType lostFoundType = new LostFoundType();
        lostFoundType.setId(typeID);
        lost.setLostFoundType(lostFoundType);
        User user = new User();
        user.setId(userID);
        lost.setUser(user);
        if (imgFile.getOriginalFilename().equals("")){
            lost.setImg("default.jpg");
        }else {
            lost.setImg(uploadImage(imgFile));
        }
        boolean flag = lostService.addLost(lost);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    /**
     * 增加失物信息点击量
     * @param lostID 失物信息编号
     * @return
     */
    @PutMapping("/updateClickCount/{lostID}")
    public Result updateClickCount(@PathVariable int lostID){
        boolean flag = lostService.updateClickCount(lostID);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 用户修改失物信息
     * @param lost
     * @return
     */
    @PutMapping("/userUpdateLost")
    public Result userUpdateLost(@RequestBody Lost lost){
        boolean flag = lostService.userUpdateLost(lost);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 用户删除个人发布的失物信息
     * @param lostID 失物信息编号
     * @return
     */
    @DeleteMapping("/userDeleteLost")
    public Result userDeleteLost(@RequestParam("lostID") int lostID){
        boolean flag = lostService.userDeleteLost(lostID);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }

    /**
     * 删除失物信息
     * @param lostID 招领信息编号
     * @return
     */
    @DeleteMapping("/deleteLost/{lostID}")
    public Result deleteLost(@PathVariable Integer lostID){
        boolean flag = lostService.userDeleteLost(lostID);
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
        PageBean<Lost> lostPageBean = lostService.selectByPage(currentPage,pageSize,"未找到");
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，无未找到的信息。";
        return new Result(code,lostPageBean,msg);
    }
    /**
     *查询已找到的所有分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @return
     */
    @PostMapping("/find")
    public Result find(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Lost> lostPageBean = lostService.selectByPage(currentPage,pageSize,"已找到");
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，无已找到的信息。";
        return new Result(code,lostPageBean,msg);
    }
    /**
     *查询所有的分页信息
     * @param currentPage 当前页码
     * @param pageSize 每页展示条数
     * @return
     */
    @PostMapping("/all")
    public Result all(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Lost> lostPageBean = lostService.selectByPage(currentPage,pageSize,null);
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，没有失物信息。";
        return new Result(code,lostPageBean,msg);
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
        PageBean<Lost> lostPageBean = lostService.selectByPageAndType(currentPage,pageSize,typeID,"未找到");
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该分类下无未找到的信息。";
        return new Result(code,lostPageBean,msg);
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
        PageBean<Lost> lostPageBean = lostService.selectByPageAndType(currentPage,pageSize,typeID,"已找到");
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该分类下无已找到的信息。";
        return new Result(code,lostPageBean,msg);
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
        PageBean<Lost> lostPageBean = lostService.selectByPageAndType(currentPage,pageSize,typeID,null);
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该分类下没有失物信息。";
        return new Result(code,lostPageBean,msg);
    }

    /**
     *不根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param condition 失物信息的状态
     * @return
     */
    @PostMapping("/getSelfLost")
    public Result getSelfLost(@RequestParam("userID") int userID,@RequestParam("condition") String condition) {
        List<Lost> lostList = lostService.getSelfLost(userID,condition);
        Integer code = lostList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostList.size() != 0 ? "查找成功。" : "查询失败，没有失物信息。";
        return new Result(code,lostList,msg);
    }

    /**
     *根据分类获取个人的招领信息
     * @param userID 用户编号
     * @param condition 失物信息的状态
     * @param typeID 分类编号
     * @return
     */
    @PostMapping("/getSelfLostAndType")
    public Result getSelfLostAndType(@RequestParam("userID") int userID, @RequestParam("typeID") int typeID,@RequestParam("condition") String condition) {
        List<Lost> lostList = lostService.getSelfLostAndType(userID,typeID,condition);
        Integer code = lostList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostList.size() != 0 ? "查找成功。" : "查询失败，没有失物信息。";
        return new Result(code,lostList,msg);
    }

    /**
     * 修改招领信息状态为已找到
     * @param lostID
     * @return
     */
    @PutMapping("/updateStateForTrue/{lostID}")
    public Result updateStateForTrue(@PathVariable int lostID){

        boolean flag = lostService.updateStateForTrue(lostID);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 根据失物信息编号增加点击量，并获取同类型的信息
     * @param lostID 招领信息编号
     * @param typeID 分类编号
     * @return
     */
    @PostMapping("/getSimilarLost")
    public Result getSimilarLost(@RequestParam("lostID") int lostID,@RequestParam("typeID")int typeID){
        List<Lost> lostList = lostService.getSimilarLost(lostID,typeID);
        Integer code = lostList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostList.size() != 0 ? "查找成功，结果如下：" : "查询失败，无置顶的招领信息。";
        return new Result(code,lostList,msg);
    }

    /**
     * 管理员删除失物信息（可批量删除）
     * @param lostIDs 失物信息编号的数组
     * @return
     */
    @DeleteMapping("/adminDeleteLost")
    public Result adminDeleteLost(@RequestParam("lostIDs[]")int[] lostIDs){
        boolean flag = lostService.adminDeleteLost(lostIDs);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
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
        PageBean<Lost> lostPageBean = lostService.getDateBySearch(searchTitle,searchPlace,typeID,condition,currentPage,pageSize);
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该分类下无已找到的信息。";
        return new Result(code,lostPageBean,msg);
    }

    /**
     * 查询信息
     * @param searchTitle 标题
     * @param searchPlace 地点
     * @param typeID 分类
     * @param stick 是否置顶
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
        PageBean<Lost> lostPageBean = lostService.getDate(searchTitle,searchPlace,typeID,stick,sort,condition,currentPage,pageSize);
        Integer code = lostPageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostPageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无失物信息。";
        return new Result(code,lostPageBean,msg);
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
