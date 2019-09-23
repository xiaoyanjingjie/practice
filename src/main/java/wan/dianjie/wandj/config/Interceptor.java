package wan.dianjie.wandj.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import freemarker.template.utility.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wan.dianjie.wandj.base.ReturnResult;
import wan.dianjie.wandj.customannotations.Auth;
import wan.dianjie.wandj.entidy.User;
import wan.dianjie.wandj.mapper.UserMapper;

/**
 * 实现登入拦截
 *
 * @author wan dianjie
 * @date 2019-07-30 17:41
 */
@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {
//  @Autowired
//  UserMapper userMapper;
  @Autowired
  private RedisTemplate redisTemplate;

  @Override
  public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
      throws Exception {
    // TODO Auto-generated method stub
  }

  @Override
  public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
      throws Exception {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.info("================================================进入拦截器==============================================================================");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
    System.out.println("------------------>:已完成跨域处理");
    System.out.println("preHandle");
    log.info("请求的URL为：{}",request.getRequestURL());
    // 从参数中取出用户身份并验证
   // User admin = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId,request.getParameter("id")));
    String token = request.getHeader("token");
    if (!token.equals(redisTemplate.opsForValue().get(token))) {
      System.out.println("permission denied");
      returnJson(response,"无权限/permission denied");
      return false;
    }

    return true;
  }

  private void returnJson(HttpServletResponse response, String json) throws Exception {
    PrintWriter writer = null;
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=utf-8");
    try {
      writer = response.getWriter();
      writer.print(new ReturnResult<>(-1,json));

    } catch (IOException e) {
      log.error("response error", e);
    } finally {
      if (writer != null)
      {  writer.close();}
    }
  }


}
