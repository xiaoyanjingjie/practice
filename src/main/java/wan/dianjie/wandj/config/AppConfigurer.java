package wan.dianjie.wandj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 我们还要注册一下此拦截器才能被springboot检测到，在config包下建立AppConfigurer类，代码如下：
 *
 * @author wan dianjie
 * @date 2019-07-30 17:46
 */
@Slf4j
@Configuration
public class AppConfigurer extends WebMvcConfigurerAdapter {

  @Bean
  public HandlerInterceptor getMyInterceptor() {
    return new Interceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    log.info("********addInterceptors添加成功！*************");
    registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
    super.addInterceptors(registry);
  }
}