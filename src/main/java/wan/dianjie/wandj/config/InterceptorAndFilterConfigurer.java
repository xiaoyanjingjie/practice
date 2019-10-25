package wan.dianjie.wandj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 我们还要注册一下此拦截器才能被springboot检测到，
 * 在config包下建立AppConfigurer类，代码如下：
 * 配置过滤器
 *
 *
 * @author wan dianjie
 * @date 2019-07-30 17:46
 */
@Slf4j
@Configuration
public class InterceptorAndFilterConfigurer implements WebMvcConfigurer {

  @Bean
  public HandlerInterceptor getMyInterceptor() {
    return new Interceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    log.info("********addInterceptors添加成功！*************");
    registry.addInterceptor(getMyInterceptor()).excludePathPatterns("/login/**","/view/**","/order/**","/es/**","/quartz/**","/job/**","/error").addPathPatterns("/**");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**")
        .addResourceLocations("classpath:/static/");
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Bean
  public FilterRegistrationBean filterRegist() {
    FilterRegistrationBean frBean = new FilterRegistrationBean();
    frBean.setFilter(new MyFilter());
    frBean.addUrlPatterns("/test/*");
    System.out.println("filter");
    return frBean;
  }

//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/*", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        // 这个顺序很重要哦，为避免麻烦请设置在最前
//        bean.setOrder(0);
//        bean.setFilter(new MyFilter());
//        bean.addUrlPatterns("/*");
//        return bean;
//    }
//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/*")
//        .allowedOrigins("*")
//        .allowedMethods("GET", "POST", "OPTIONS")
//        .allowedHeaders("*")
//        .allowCredentials(true)
//        .maxAge(3600);
//  }

}