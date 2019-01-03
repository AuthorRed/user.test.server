package cn.author.tradewebtest.web;

import cn.author.tradewebtest.entity.SysUser;
import cn.author.tradewebtest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger("TestController");
    @Autowired
    private UserService userService;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder ;
    @GetMapping(value = "/user/page/{number}")
    public  List<SysUser> getPage(@PathVariable(name = "number") Integer number){
        log.info("start time:");
        List<SysUser> list = userService.list(new HashMap<String, Object>(), number);
        log.info("end time:");
        return list;
    }
    @GetMapping(value = "/user/{id}")
    public SysUser getById(@PathVariable(name = "id") Long id){
        log.info("start time:");
        SysUser user = userService.getById(id);
        log.info("end time:");
        return user;
    }
    @GetMapping(value = "/user/{userName}")
    public SysUser testLongConnectionNotInitTemplate(@PathVariable(name = "userName") String userName){
        log.info("start time:");
        SysUser user = userService.getUser(userName);
        log.info("end time:");
        return user;
    }
    @PostMapping(value = "/user/{number}")
    public String addUser(@PathVariable(name = "number") Integer number){
        log.info("start time:");
        if (number==null){
            number=1;
        }
        for(int i=0;i<number;i++){
            userService.save(getUser());
        }
        log.info("end time:");
        return "success";
    }
    @DeleteMapping(value = "/user/{userId}")
    public String deleteUser(@PathVariable(name = "userId") Long userId){
        log.info("start time:");
        userService.deleteUser(userId);
        log.info("end time:");
        return "success";
    }


    private SysUser getUser(){
        String name = UUID.randomUUID().toString().substring(0,6);
        SysUser sysUser = new SysUser();
        sysUser.setUsername(name);
        sysUser.setSex(1);
        sysUser.setPassword("123123");
        sysUser.setNickname("nickname"+name);
        sysUser.setEmail(name+"@163.com");
        sysUser.setTelephone("131231231");
        sysUser.setStatus(1);
        return sysUser;
    }
}

