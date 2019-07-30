package wan.dianjie.wandj.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybaitsplus分页差价
 *
 * @author wan dianjie
 * @date 2019-06-19 20:28
 */
//Spring boot方式
@EnableTransactionManagement
@Configuration
@MapperScan("wan.dianjie.wandj.mapper*")
public class MybatisPlusConfig {

  /**
   * 分页插件
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }
}
