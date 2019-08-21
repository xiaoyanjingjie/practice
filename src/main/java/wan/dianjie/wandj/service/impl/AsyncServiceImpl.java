package wan.dianjie.wandj.service.impl;

import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import wan.dianjie.wandj.config.log.LogServiceFilter;
import wan.dianjie.wandj.entidy.User;
import wan.dianjie.wandj.mapper.UserMapper;

/**
 * @author wan dianjie
 * @date 2019-06-03 18:47
 */
@Slf4j
@Service
public class AsyncServiceImpl {

  @Async(value = "taskExecutorPool")
  public Future asyncTest() throws InterruptedException {
    log.info("当前正在执行的线程name:{},活跃数量：{}",Thread.currentThread().getName(),Thread.activeCount());
    return new AsyncResult<>("");

  }
}
