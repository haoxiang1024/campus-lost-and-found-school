package com.xiaolinzi.lostandfound.service.impl;

import com.xiaolinzi.lostandfound.mapper.AdminMapper;
import com.xiaolinzi.lostandfound.model.Admin;
import com.xiaolinzi.lostandfound.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//事务管理器
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getAll() {
        return adminMapper.getAll();
    }

    @Override
    public boolean adminUpdatePhoto(int adminID, String photo) {
        return adminMapper.adminUpdatePhoto(adminID,photo)>0;
    }

    @Override
    public Admin login(String account, String password) {
        return adminMapper.login(account,password);
    }

    @Override
    public boolean adminUpdateSelf(Admin admin) {
        return adminMapper.adminUpdateSelf(admin)>0;
    }

    @Override
    public boolean adminUpdatePassword(Admin admin) {
        return adminMapper.adminUpdatePassword(admin)>0;
    }

    @Override
    public Admin getAdminByID(int adminID) {
        return adminMapper.getAdminByID(adminID);
    }
}
