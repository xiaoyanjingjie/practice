package wan.dianjie.wandj.kafkaconfig;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * kafka确认机制
 *
 * @author wan dianjie
 * @date 2019-08-05 16:01
 */
@Slf4j
@Component
public class AckKafkaListener {
  @Value("${spring.kafka.bootstrap-servers}")
  private String KAFKA_SERVICE;

  @Autowired
  private KafkaTemplate kafkaTemplate;

  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>();
    log.info("------KAFKA_SERVICE------{}",KAFKA_SERVICE);
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVICE);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return props;
  }

  @Bean("ackContainerFactory")
  public ConcurrentKafkaListenerContainerFactory ackContainerFactory() {
    ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
    factory.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
    factory.setAutoStartup(false);
    factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
    return factory;
  }

  /**
   * 第一个是参数是topic名字，第二个参数是分区个数，第三个是topic的复制因子个数
   *         //当broker个数为1个时会创建topic失败，
   *         //提示：replication factor: 2 larger than available brokers: 1
   *         //只有在集群中才能使用kafka的备份功能
   * ---------------------
   * 版权声明：本文为CSDN博主「原来丨」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
   * 原文链接：https://blog.csdn.net/qq_36027670/article/details/79488880
   * @return
   */
  @Bean
  public NewTopic initialTopic() {
    return new NewTopic("ack",5, (short) 1 );
  }

  /**
   * https://www.jianshu.com/p/a64defb44a23
   *
   * 使用Ack机制确认消费
   * Kafka的Ack机制相对于RabbitMQ的Ack机制差别比较大，刚入门Kafka的时候我也被搞蒙了，不过能弄清楚Kafka是怎么消费消息的就能理解Kafka的Ack机制了
   * 我先说说RabbitMQ的Ack机制，RabbitMQ的消费可以说是一次性的，也就是你确认消费后就立刻从硬盘或内存中删除，而且RabbitMQ粗糙点来说是顺序消费，像排队一样，一个个顺序消费，未被确认的消息则会重新回到队列中，等待监听器再次消费。
   * 但Kafka不同，Kafka是通过最新保存偏移量进行消息消费的，而且确认消费的消息并不会立刻删除，所以我们可以重复的消费未被删除的数据，当第一条消息未被确认，而第二条消息被确认的时候，Kafka会保存第二条消息的偏移量，也就是说第一条消息再也不会被监听器所获取，除非是根据第一条消息的偏移量手动获取。
   *
   * 使用Kafka的Ack机制比较简单，只需简单的三步即可：
   *
   * 设置ENABLE_AUTO_COMMIT_CONFIG=false，禁止自动提交
   * 设置AckMode=MANUAL_IMMEDIATE
   * 监听方法加入Acknowledgment ack 参数
   *
   * 作者：viu_astray
   * 链接：https://www.jianshu.com/p/a64defb44a23
   * 来源：简书
   * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
   *
   * KafkaListener注解的使用
   * topicPartitions = {@TopicPartition(topic = "ack",partitions = {"1"},partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "149"))},
   *
   * , Consumer consumer
   */
  //@KafkaListener(groupId = "wanjieGroup2", topics = {"ack"},containerFactory = "ackContainerFactory")
  public void ackListener(ConsumerRecord<?,?> record, Acknowledgment ack,@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,@Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer patitionId,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,@Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long ts) {
       log.info("ack receive ---====-----: " + record);
    log.info("ack receive Header ---====-----: " +"key :" + key +",patitionId:"+ patitionId +",topic:"+ topic + ",ts:"+ts) ;
    //如果偏移量为偶数则确认消费，否则拒绝消费
    if (record.offset() % 2 == 0) {
      log.info(record.offset()+"--ack");
      ack.acknowledge();
    } else {
      log.info(record.offset()+"--nack--出现");
      final Headers add = record.headers().add(String.valueOf(record.offset()), new byte[1]);
      //kafkaTemplate.send("ack",record.value());
      //consumer.seek(new TopicPartition("ack",record.partition()),record.offset() );
    }
  }
}
