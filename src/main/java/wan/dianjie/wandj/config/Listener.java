package wan.dianjie.wandj.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka监听
 *
 * @author wan dianjie
 * @date 2019-06-10 15:27
 */
@Slf4j
@Component
public class Listener {
//  @KafkaListener(topics = {"test"})
//  public void listen(ConsumerRecord<?, ?> record) {
//    log.info("kafka的key: " + record.key());
//    log.info("kafka的value: " + record.value().toString());
//  }
}
