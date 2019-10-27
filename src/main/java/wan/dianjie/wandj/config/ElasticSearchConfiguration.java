package wan.dianjie.wandj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * ES 配置
 *
 * @author wan dianjie
 * @date 2019-10-27 14:39
 */
@Slf4j
public class ElasticSearchConfiguration implements InitializingBean {
  /**
   * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
   * 解决netty冲突后初始化client时还会抛出异常
   * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
   */
  static {
    System.setProperty("es.set.netty.runtime.available.processors", "false");
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("*****************es_config*************************");
    log.info("es.set.netty.runtime.available.processors:{}", System.getProperty("es.set.netty.runtime.available.processors"));
    log.info("***************************************************");
  }
}
