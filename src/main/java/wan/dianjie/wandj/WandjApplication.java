package wan.dianjie.wandj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableAsync
@EnableCaching
@SpringBootApplication(scanBasePackages = "wan.dianjie.wandj")
public class WandjApplication {

  public static void main(String[] args) {
    /**
     * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
     * 解决netty冲突后初始化client时还会抛出异常
     * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
     */
    System.setProperty("es.set.netty.runtime.available.processors", "false");
    SpringApplication.run(WandjApplication.class, args);
  }
  /**
   * 解决前后端分离跨域问题
   *
   * @return
   */
  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
  

}
