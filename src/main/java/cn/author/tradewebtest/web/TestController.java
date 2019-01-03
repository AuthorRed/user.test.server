package cn.author.tradewebtest.web;

import cn.author.tradewebtest.entity.SysUser;
import cn.author.tradewebtest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger log = LoggerFactory.getLogger("TestController");
    @Autowired
    private UserService userService;
    @Autowired
    private TaskExecutor taskExecutor;
    @GetMapping(value = "/getMapping")
    public String getMapping(String name){
        return name;
    }
    @GetMapping(value = "/hello")
    public SysUser sayhello(String name){
        log.info("request param name:"+name);
        long start = System.currentTimeMillis();
        for (int i = 0;i<5;i++){
            taskExecutor.execute(new Runnable(){
                public void run() {
                    try {
                        Thread.currentThread().sleep(1000L);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    log.info("哈哈哈哈啊哈哈"+name);
                }
            });
        }
        long end = System.currentTimeMillis();
        log.info("多线程用时"+Long.toString(end-start));
        return userService.getUser(name);
    }
    @GetMapping(value = "/hello1")
    public SysUser sayhello1(String name){
        log.info("request param name:"+name);
        long start = System.currentTimeMillis();
        for (int i = 0;i<5;i++){
            try {
               Thread.currentThread().sleep(1000L);
                    log.info("哈哈哈哈啊哈哈"+name);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        log.info("多线程用时"+Long.toString(end-start));
        return userService.getUser(name);
    }
}

