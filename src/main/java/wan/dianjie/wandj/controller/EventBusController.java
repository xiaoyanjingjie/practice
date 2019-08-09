package wan.dianjie.wandj.controller;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wan.dianjie.wandj.eventbusbusiness.LoginEvent;
import wan.dianjie.wandj.eventbusbusiness.LoginMsg;
import wan.dianjie.wandj.eventbusbusiness.LoginSubscriber;
import wan.dianjie.wandj.tool.eventbus.LocalEventBus;

/**
 * 事件总线测试
 * ttps://www.jianshu.com/p/4efbfdc01cf6
 * @author wan dianjie
 * @date 2019-08-09 15:08
 */
@Slf4j
@RestController
@RequestMapping("/bus")
public class EventBusController {
  @Autowired
  private LocalEventBus localEventBus;
  @Autowired
  EventBus eventBus;
  @Autowired
  LoginSubscriber l;

  @GetMapping
  public void bus(){
    LoginMsg msg = new LoginMsg("uid", "mobile", "ip", "osVersion", "deviceModel", "deviceToken");
    localEventBus.post(new LoginEvent(msg));
    //eventBus.post(new LoginEvent(msg));
    log.info(l.getUid());
  }

}
