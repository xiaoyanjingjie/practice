package wan.dianjie.wandj.eventbusbusiness;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 3、定义订阅者
 *
 * @author wan dianjie
 * @date 2019-08-09 15:48
 */
@Slf4j
@Component
public class LoginSubscriber {

  public String uid;

    @Subscribe()
    public void onLogin(LoginEvent event) {
      LoginMsg msg = event.getContent();
       uid = msg.getUid();
      // 具体
      log.info("进入event 事件:"+uid);
  }

  public String getUid(){
    log.info("进入event 事件:"+uid);
    return uid;
  }
  @Subscribe()
  public void onLogin123(LoginEvent01 event) {
    LoginMsg msg = event.getContent();
    uid = "ssss";
    // 具体
    log.info("进入event 事件:"+uid);
  }
}
