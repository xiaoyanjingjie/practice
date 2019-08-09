package wan.dianjie.wandj.tool.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wan dianjie
 * @date 2019-08-09 15:40
 */
@Service
public class LocalEventBusImpl implements LocalEventBus {
  @Autowired
  private EventBus eventBus;

  @Override
  public void post(Event event) {
    if (null != event) {
      eventBus.post(event);
    }

  }
}
