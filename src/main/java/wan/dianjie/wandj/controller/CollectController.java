package wan.dianjie.wandj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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
      kafkaTemplate.send("test", "key", message);
      log.info("发送kafka成功.");
      return "发送kafka成功";
    } catch (Exception e) {
      log.error("发送kafka失败", e);
      return "发送kafka失败";
    }
  }

}
