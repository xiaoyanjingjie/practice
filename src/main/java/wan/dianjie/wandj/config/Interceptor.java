package wan.dianjie.wandj.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wan.dianjie.wandj.customannotations.Auth;
import wan.dianjie.wandj.entidy.User;
import wan.dianjie.wandj.mapper.UserMapper;

/**
 * 实现登入拦截
 *
 * @author wan dianjie
 * @date 2019-07-30 17:41
 */
@Component
public class Interceptor implements HandlerInterceptor {
  @Autowired
  UserMapper userMapper;

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

    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
    System.out.println("------------------>:已完成跨域处理");
    System.out.println("preHandle");

//    if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
//      System.out.println("cat cast handler to HandlerMethod.class");
//      return true;
//    }
    // 获取注解
//    Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
//    if (auth == null) {
//      System.out.println("cant find @Auth in this uri:" + request.getRequestURI());
//      return true;
//    }
    // 从参数中取出用户身份并验证
    User admin = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId,request.getParameter("id")));
    if (admin == null) {
      System.out.println("permission denied");
      response.setStatus(403);
      return false;
    }

    return true;
  }


}
