package com.jun.dao;

import com.jun.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMapper {

    public User findById(@Param("id") Integer id);

    public Integer insertUser(User user);

    public User findByUsernameAndPassword(@Param("username") String name,@Param("password") String password);
}
