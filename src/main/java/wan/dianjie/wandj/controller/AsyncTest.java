package wan.dianjie.wandj.controller;

import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wan.dianjie.wandj.entidy.User;
import wan.dianjie.wandj.mapper.UserMapper;
import wan.dianjie.wandj.service.impl.AsyncServiceImpl;

/**
 * 自定义异步线程池测试
 *
 * @author wan dianjie
 * @date 2019-06-03 10:42
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class AsyncTest {
  @Autowired
  private AsyncServiceImpl asyncService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  UserMapper userMapper;

  @GetMapping("/asyncTest1")
  public String asyncTest1() throws InterruptedException {
    //测试redis
    redisTemplate.opsForValue().set("11","11");
   Object object = redisTemplate.opsForValue().get("11");
   log.info("object:{}",object);
    //此方法返回活动线程的当前线程的线程组中的数量。
    log.info("当前正在执行的线程name:{},存活线程数量：{}：{}",Thread.currentThread().getName(),Thread.activeCount());
    Future r =  asyncService.asyncTest();
    while (true){
      if(r.isDone()){
        break;
      }
      Thread.sleep(1000);
    }
    User u =  userMapper.selectById("402880e74d75c4dd014d75d44af30005");
    log.info("u:{}",u);
    return "1233";

  }
}
