package com.xiaolinzi.lostandfound.controller;

import com.xiaolinzi.lostandfound.model.PageBean;
import com.xiaolinzi.lostandfound.model.Type;
import com.xiaolinzi.lostandfound.service.LostFoundTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class StatController {

    @Autowired
    private LostFoundTypeService lostFoundTypeService;

    @GetMapping("/stat")
    public Result getAllLostFoundType(String method){
//        System.out.println(method);
        PageBean<Type> typePageBean = lostFoundTypeService.getTypeDate("","","",1,lostFoundTypeService.getAll().size());
        Integer code = typePageBean.getTotalCount() != 0 ? Code.GET_OK : Code.GET_ERR;
        String msg = typePageBean.getTotalCount() != 0 ? "查找成功。" : "查询失败，该条件下无招领信息。";
        return new Result(code,typePageBean,msg);
//        return null;
    }

}
