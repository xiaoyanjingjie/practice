package wan.dianjie.wandj.kafkaconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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

  /**
   * #kafka配置信息
   * kafka:
   *   producer:
   *     bootstrap-servers: 10.161.11.222:6667,10.161.11.223:6667,10.161.11.224:6667
   *     batch-size: 16785                                   #一次最多发送数据量
   *     retries: 1                                          #发送失败后的重复发送次数
   *     buffer-memory: 33554432                             #32M批处理缓冲区
   *     linger: 1
   *   consumer:
   *     bootstrap-servers: 10.161.11.222:6667,10.161.11.223:6667,10.161.11.224:6667
   *     auto-offset-reset: latest                           #最早未被消费的offset earliest
   *     max-poll-records: 3100                              #批量消费一次最大拉取的数据量
   *     enable-auto-commit: false                           #是否开启自动提交
   *     auto-commit-interval: 1000                          #自动提交的间隔时间
   *     session-timeout: 20000                              #连接超时时间
   *     max-poll-interval: 15000                            #手动提交设置与poll的心跳数,如果消息队列中没有消息，等待毫秒后，调用poll()方法。如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
   *     max-partition-fetch-bytes: 15728640                 #设置拉取数据的大小,15M
   *   listener:
   *     batch-listener: true                                #是否开启批量消费，true表示批量消费
   *     concurrencys: 3,6                                   #设置消费的线程数
   *     poll-timeout: 1500                                  #只限自动提交，
   * ---------------------
   * 版权声明：本文为CSDN博主「大小鱼鱼鱼与鱼.」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
   * 原文链接：https://blog.csdn.net/qq_26869339/article/details/88324980
   */
  @Value("${spring.kafka.bootstrap-servers}")
  private String KAFKA_SERVICE;

  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVICE);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);//自动提交
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 120000);
    props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);
    //一次拉取消息数量
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1000");
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
    container.setConcurrency(1);
    //设置为批量监听
    container.setBatchListener(true);
    //轮训的超时时间
    container.getContainerProperties().setPollTimeout(3000);
    return container;
  }

  //@KafkaListener(groupId = "wanjieBatch", clientIdPrefix = "batch", topics = {
   //   "batch"}, containerFactory = "batchContainerFactory")
  public void batchListener(List<ConsumerRecord<?,?> > recordList) {

    log.info(" --------batch  receive : " + recordList.size());
    for (ConsumerRecord s : recordList) {
      log.info("------:"+s);
    }
  }

}