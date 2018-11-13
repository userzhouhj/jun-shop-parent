package com.jun.service;


import com.jun.domain.User;

public interface MemberService {

    public User findByUserId(Integer id);

    public Integer saveUser(User user);

    public User findByUsernameAndPassword(String name,String password);

}
