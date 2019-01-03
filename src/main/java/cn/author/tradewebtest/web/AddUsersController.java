package cn.author.tradewebtest.web;

import cn.author.tradewebtest.entity.SysUser;
import cn.author.tradewebtest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@RestController
public class AddUsersController {
    private static final Logger log = LoggerFactory.getLogger("TestController");
    @Autowired
    private UserService userService;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder ;
    @GetMapping(value = "/testLongConnectionNotInitTemplate")
    public String testLongConnectionNotInitTemplate(String name){
        log.info("start time:");
        RestTemplate restTemplate = restTemplateBuilder.build();
        log.info("build restTemplate:");
        for (int i = 0;i<50;i++){
            taskExecutor.execute(new Runnable(){
                public void run() {
                    String remoteUser = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
                    log.info("this is remoteUser:"+remoteUser);
                }
            });
        }
        log.info("end time:");
        return name;
    }
    @GetMapping(value = "/testLongConnectionThreadpoolConcurrent")
    public String testLongConnectionThreadpoolConcurrent(String name){
        log.info("start time:");
        for (int i = 0;i<50;i++){
            taskExecutor.execute(new Runnable(){
                public void run() {
                    String remoteUser = restTemplateBuilder.build().getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
                    log.info("this is remoteUser:"+remoteUser);
                }
            });
        }
        log.info("end time:");
        return name;
    }
    @GetMapping(value = "/testLongConnection2")
    public String testLongConnection2(String name){
        log.info("request param name:"+name);
        long start = System.currentTimeMillis();
        SysUser user = getUser();
//        http://localhost:8080/index.html
        RestTemplate restTemplate = restTemplateBuilder.build();
        System.out.println("=================================================");
        String remoteUser0 = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
        System.out.println("=====remoteUser1============================================"+remoteUser0);
        for(int i=1;i<51;i++){
            String remoteUser1 = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
            System.out.println("=====remoteUser"+i+"============================================"+remoteUser1+i);
        }
        long end = System.currentTimeMillis();
        log.info("线程用时"+Long.toString(end-start));
        return name;
    }
    @GetMapping(value = "/testLongConnection")
    public String testLongConnection(String name){
        log.info("request param name:"+name);
        long start = System.currentTimeMillis();
        SysUser user = getUser();
//        http://localhost:8080/index.html
        RestTemplate restTemplate = restTemplateBuilder.build();
        for (int i = 0;i<5;i++){
            taskExecutor.execute(new Runnable(){
                public void run() {
                    String remoteUser = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
                    log.info("this is remoteUser:"+remoteUser);
                }
            });
        }
        System.out.println("=================================================");
        String remoteUser1 = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
        System.out.println("=====remoteUser1============================================"+remoteUser1);
        String remoteUser2 = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
        System.out.println("=====remoteUser2============================================"+remoteUser2);
        String remoteUser3 = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
        System.out.println("=====remoteUser3============================================"+remoteUser3);
        String remoteUser4 = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
        System.out.println("=====remoteUser4============================================"+remoteUser4);
        String remoteUser5 = restTemplate.getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
        System.out.println("=====remoteUser5============================================"+remoteUser5);

        long end = System.currentTimeMillis();
        log.info("线程用时"+Long.toString(end-start));
        return name;
    }
    @GetMapping(value = "/testRemoteGet")
    public String sayhello(String name){
        log.info("request param name:"+name);
        long start = System.currentTimeMillis();
        SysUser user = getUser();
//        http://localhost:8080/index.html
        for (int i = 0;i<5;i++){
            taskExecutor.execute(new Runnable(){
                public void run() {
                    String remoteUser = restTemplateBuilder.build().getForObject("http://localhost:8081/getMapping?name="+name,  String.class);
                    log.info("this is remoteUser:"+remoteUser);
                }
            });
        }

        long end = System.currentTimeMillis();
        log.info("线程用时"+Long.toString(end-start));
        return name;
    }

    private SysUser getUser(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String name = uuid.substring(0, 8);
        SysUser sysUser = new SysUser();
        sysUser.setBirthday(new Date());
        sysUser.setEmail(name+"abc@com.163.cn");
        sysUser.setNickname(name);
        sysUser.setPassword("123123");
        sysUser.setPhone("13892368239");
        sysUser.setSex(1);
        sysUser.setStatus(1);
        sysUser.setUsername(name);
        return sysUser;
    }
}

