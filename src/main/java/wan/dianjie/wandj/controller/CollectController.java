package wan.dianjie.wandj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wan.dianjie.wandj.entidy.User;
import wan.dianjie.wandj.mapper.UserMapper;


/**
 * kafka producer
 *
 * @author wan dianjie
 * @date 2019-06-10 15:13
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class CollectController {

  @Autowired
  private KafkaTemplate kafkaTemplate;
  @Autowired
  UserMapper userMapper;

  @RequestMapping(value = "/send", method = RequestMethod.GET)
  public String sendKafka(Page page,HttpServletRequest request, HttpServletResponse response) {
   IPage page1 = userMapper.selectPage(page,Wrappers.<User>lambdaQuery().ne(User::getId,"s"));
    try {
      String message = request.getParameter("message");
      log.info("kafka的消息={}",message);
      ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send("test", 0,"key", message);
      log.info("----listenableFuture-----:{}",listenableFuture.get());
      listenableFuture.addCallback(new SuccessCallback() {
        @Override
        public void onSuccess(Object o) {
          log.info("+++++++++success+++++++++");
        }
      },new FailureCallback(){

        @Override
        public void onFailure(Throwable throwable) {
           log.info("-----------fail-------------");
        }
      });
      log.info("发送kafka成功.");
      return listenableFuture.get().toString();
    } catch (Exception e) {
      log.error("发送kafka失败", e);
      return "发送kafka失败";
    }


  }
  @RequestMapping(value = "/acksend", method = RequestMethod.GET)
  public void acksend(Page page,HttpServletRequest request, HttpServletResponse response)
      throws ExecutionException, InterruptedException {

    for (int i = 0; i < 5; i++) {
      Map map = new HashMap<>();
      map.put(KafkaHeaders.TOPIC, "ack");
      map.put(KafkaHeaders.MESSAGE_KEY, i+"");
      map.put(KafkaHeaders.PARTITION_ID, 1);
      map.put(KafkaHeaders.TIMESTAMP, System.currentTimeMillis());
      ListenableFuture ack = kafkaTemplate.send(new GenericMessage<>("test Header",map));
      log.info("acksenf{}:",ack.get());
    }

  }

  @RequestMapping(value = "/acksendbatch", method = RequestMethod.GET)
  public void testBatch() {
    for (int i = 0; i < 12; i++) {
      kafkaTemplate.send("batch", "test batch listener,dataNum-" + i);
    }
  }
}
