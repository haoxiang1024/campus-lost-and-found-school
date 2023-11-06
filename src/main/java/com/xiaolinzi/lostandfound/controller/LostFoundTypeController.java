package com.xiaolinzi.lostandfound.controller;

import com.xiaolinzi.lostandfound.model.*;
import com.xiaolinzi.lostandfound.service.LostFoundTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/LostFoundType")
public class LostFoundTypeController {

    @Autowired
    private LostFoundTypeService lostFoundTypeService;

    @GetMapping("/getAll")
    public Result getAllLostFoundType(){
        List<LostFoundType> lostFoundTypeList = lostFoundTypeService.getAll();
        Integer code = lostFoundTypeList.size() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostFoundTypeList.size() != 0 ? "查找成功。" : "查询失败，没有失物招领分类信息。";
        return new Result(code,lostFoundTypeList,msg);
    }


    @PostMapping("/getDate")
    public Result getDate(@RequestParam("typeName") String typeName,@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<LostFoundType> lostFoundTypePageBean = lostFoundTypeService.getDate(typeName,currentPage,pageSize);
        Integer code = lostFoundTypePageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = lostFoundTypePageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无招领信息。";
        return new Result(code,lostFoundTypePageBean,msg);
    }

    @PostMapping("/getTypeDate")
    public Result getTypeDate(@RequestParam("typeName") String typeName,@RequestParam("foundSort") String foundSort,
                              @RequestParam("lostSort") String lostSort,@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        PageBean<Type> typePageBean = lostFoundTypeService.getTypeDate(typeName,foundSort,lostSort,currentPage,pageSize);
        Integer code = typePageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = typePageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无招领信息。";
        return new Result(code,typePageBean,msg);
    }

    @PostMapping("/addType")
    public Result addType(@RequestBody LostFoundType lostFoundType) {

        boolean flag = lostFoundTypeService.addType(lostFoundType);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    @PutMapping("/updateType")
    public Result updateType(@RequestBody LostFoundType lostFoundType){
//        System.out.println(lostFoundType);
        boolean flag = lostFoundTypeService.updateType(lostFoundType);
//        System.out.println(flag);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 删除分类信息
     * @param typeID 分类信息编号
     * @return
     */
    @DeleteMapping("/deleteType/{typeID}")
    public Result deleteType(@PathVariable Integer typeID){
        boolean flag = lostFoundTypeService.deleteType(typeID);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }
}
