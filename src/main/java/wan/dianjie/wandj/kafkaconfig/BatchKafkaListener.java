package wan.dianjie.wandj.kafkaconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

/**
 * kafka批量消费
 *批量消费案例
 *
 * 重新创建一份新的消费者配置，配置为一次拉取5条消息
 * 创建一个监听容器工厂，设置其为批量消费并设置并发量为5，这个并发量根据分区数决定，必须小于等于分区数，否则会有线程一直处于空闲状态
 * 创建一个分区数为8的Topic
 * 创建监听方法，设置消费id为batch，clientID前缀为batch，监听topic.quick.batch，使用batchContainerFactory工厂创建该监听容器
 *
 * 作者：viu_astray
 * 链接：https://www.jianshu.com/p/a64defb44a23
 * 来源：简书
 * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
 * @author wan dianjie
 * @date 2019-08-07 10:52
 */
@Slf4j
@Component
public class BatchKafkaListener {

  @Value("${spring.kafka.bootstrap-servers}")
  private String KAFKA_SERVICE;

  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVICE);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);//自动提交
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
    //一次拉取消息数量
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return props;
  }
  @Bean
  public NewTopic batchTopic() {
    return new NewTopic("batch", 8, (short) 1);
  }

  @Bean("batchContainerFactory")
  public ConcurrentKafkaListenerContainerFactory listenerContainer() {
    ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
    container.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
    //设置并发量，小于或等于Topic的分区数
    container.setConcurrency(5);
    //设置为批量监听
    container.setBatchListener(true);
    return container;
  }

  @KafkaListener(groupId = "wanjieBatch", clientIdPrefix = "batch", topics = {
      "batch"}, containerFactory = "batchContainerFactory")
  public void batchListener(List<String> data) {
    log.info(" --------batch  receive : ");
    for (String s : data) {
      log.info("------:"+s);
    }
  }

}