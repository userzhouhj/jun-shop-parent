package com.jun.controller;

import com.alibaba.fastjson.JSONObject;
import com.jun.base.BaseData;
import com.jun.constants.MessageConstant;
import com.jun.constants.TokenConstant;
import com.jun.domain.User;
import com.jun.mq.MessageProcider;
import com.jun.service.MemberService;
import com.jun.utils.BaseDataUtil;
import com.jun.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class MemberController implements  IMemberController{

    @Autowired
    private MemberService memberService;

    @Autowired
    private MessageProcider messageProcider;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${queue}")
    private String queue;


    @Override
    public BaseData<User> get(@PathVariable("id") Integer id) {
        User user = memberService.findByUserId(id);

        return BaseDataUtil.resultSuccess(user);
    }

    @Override
    public BaseData<String> register(@RequestBody User user) {
        Integer result = memberService.saveUser(user);
        if(result <= 0){
            return BaseDataUtil.resultFail("注册失败");
        }
        String json = emailJson(user.getEmail());
        send(json);
        return BaseDataUtil.resultSuccess("注册成功");
    }

    @Override
    public BaseData login(@RequestBody User user) {
        //1 验证参数

        //2 验证数据库
        User target = memberService.findByUsernameAndPassword(user.getUsername(),user.getPassword());

        if(target == null){
            return BaseDataUtil.resultFail("用户名不存在或者密码错误");
        }
        //3 设置tocken
        String memberToken = TokenUtil.getToken(user.getUsername());
        //4 存入缓存
        stringRedisTemplate.opsForValue().set(memberToken,target.getId().toString());
        stringRedisTemplate.expire(memberToken,TokenConstant.TOKEN_TIME, TimeUnit.SECONDS);
        //5 封装返回数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken",memberToken);
        return BaseDataUtil.resultSuccess(jsonObject);
    }

    @Override
    public BaseData loginByToken(@RequestParam("token") String token){
        //1.验证token
        if(token.equals("") || token == null){
            return BaseDataUtil.resultFail("用户令牌不能为空");
        }
        //2.通过token获取Userid
        String userId = stringRedisTemplate.opsForValue().get(token);
        log.info("########用户id: "+userId+" ############");
        //3.获取用户
        User targetUser = memberService.findByUserId(Integer.parseInt(userId));
        //4.返回数据
        if(targetUser == null){
            return BaseDataUtil.resultFail("用户不存在");
        }
        targetUser.setPassword(null);//密码保护
        return BaseDataUtil.resultSuccess(targetUser);
    }

    //对电子邮件进行封装
    private String emailJson(String email){
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        JSONObject content = new JSONObject();
        header.put(MessageConstant.MESSAGE_INTERFACETYPE,MessageConstant.MESSAGE_INTERFACETYPE_EMAIL);
        content.put(MessageConstant.MESSAGE_CONTENT_EMAIL,email);

        root.put(MessageConstant.MESSAGE_HEADER,header);
        root.put(MessageConstant.MESSAGE_CONTENT,content);

        String json = root.toString();
        return json;
    }

    private void send(String json){
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(queue);
        messageProcider.sendMsgToMq(activeMQQueue,json);
    }
}
