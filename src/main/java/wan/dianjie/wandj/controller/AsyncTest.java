package wan.dianjie.wandj.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import java.util.Collection;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wan.dianjie.wandj.base.ReturnResult;
import wan.dianjie.wandj.customannotations.Auth;
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
@RequestMapping
@RestController
public class AsyncTest {
  @Autowired
  private AsyncServiceImpl asyncService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  UserMapper userMapper;

  @Auth(user = "admin")
  @GetMapping("/test/asyncTest1")
  public ReturnResult asyncTest1() throws InterruptedException {
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
    return new ReturnResult<>();

  }
  @SentinelResource(value = "login")
  @GetMapping("/login")
  public ReturnResult login(@RequestParam String id) throws InterruptedException {
    redisTemplate.opsForValue().set(id,id,10000, TimeUnit.SECONDS);
    return new ReturnResult<>( "登入成功！id:"+id);

  }
  //@PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/testToken")
  public ReturnResult testToken() throws InterruptedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      //return false;
    }
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    return new ReturnResult<>("token 验证成功！");
  }
}
