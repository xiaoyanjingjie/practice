package wan.dianjie.wandj.config.sentinel;


import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author wan dianjie
 * @date 2020-05-14 12:30
 */
@Component
@Slf4j
public class RedisDataSourceConfig implements ApplicationRunner {

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Value("${spring.redis.password}")
  private String redisPd;

  //限流规则key前缀
  public final String RULE_FLOW = "sentinel_rule_flow_";
  public final String RULE_FLOW_CHANNEL = "sentinel_rule_flow_channel";
  //降级规则key前缀
  public final String RULE_DEGRADE = "sentinel_rule_degrade_";
  public final String RULE_DEGRADE_CHANNEL = "sentinel_rule_degrade_channel";
  //系统规则key前缀
  public final String RULE_SYSTEM = "sentinel_rule_system_";
  public final String RULE_SYSTEM_CHANNEL = "sentinel_rule_system_channel";

  /**
   * ApplicationRunner
   * 该接口的方法会在服务启动之后被立即执行
   * 主要用来做一些初始化的工作
   * 但是该方法的运行是在SpringApplication.run(…​) 执行完毕之前执行
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info(">>>>>>>>>执行sentinel规则初始化");
    RedisConnectionConfig config = RedisConnectionConfig.builder().withHost(redisHost).withPort(redisPort).withPassword(redisPd).build();

    Converter<String, List<FlowRule>> parser = source -> JSON
        .parseObject(source,new TypeReference<List<FlowRule>>() {});
    ReadableDataSource<String, List<FlowRule>> redisDataSource = new RedisDataSource<>(config, RULE_FLOW+ SentinelConfig
        .getAppName(), RULE_FLOW_CHANNEL, parser);
    FlowRuleManager.register2Property(redisDataSource.getProperty());

    Converter<String, List<DegradeRule>> parser1 = source -> JSON
        .parseObject(source,new TypeReference<List<DegradeRule>>() {});
    ReadableDataSource<String, List<DegradeRule>> redisDataSource1 = new RedisDataSource<>(config, RULE_DEGRADE+ SentinelConfig
        .getAppName(), RULE_DEGRADE_CHANNEL, parser1);
    DegradeRuleManager.register2Property(redisDataSource1.getProperty());

    Converter<String, List<SystemRule>> parser2 = source -> JSON
        .parseObject(source,new TypeReference<List<SystemRule>>() {});
    ReadableDataSource<String, List<SystemRule>> redisDataSource2 = new RedisDataSource<>(config, RULE_SYSTEM+ SentinelConfig
        .getAppName(), RULE_SYSTEM_CHANNEL, parser2);
    SystemRuleManager.register2Property(redisDataSource2.getProperty());
  }


}
