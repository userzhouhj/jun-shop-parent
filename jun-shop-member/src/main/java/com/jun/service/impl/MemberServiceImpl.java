package com.jun.service.impl;

import com.jun.dao.MemberMapper;
import com.jun.domain.User;
import com.jun.service.MemberService;
import com.jun.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public User findByUserId(Integer id) {
        return memberMapper.findById(id);
    }

    @Override
    public User findByUsernameAndPassword(String name, String password) {
        String newpassword = MD5Util.MD5(password);
        return memberMapper.findByUsernameAndPassword(name,newpassword);
    }

    @Override
    public Integer saveUser(User user) {
        String newPassword = MD5Util.MD5(user.getPassword());
        user.setPassword(newPassword);
       return memberMapper.insertUser(user);
    }
}
