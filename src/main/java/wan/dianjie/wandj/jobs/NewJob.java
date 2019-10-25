package wan.dianjie.wandj.jobs;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import wan.dianjie.wandj.service.quartz.BaseJob;

/**
 * @author wan dianjie
 * @date 2019-10-16 16:11
 */
@Slf4j
public class NewJob implements BaseJob {

  public NewJob() {

  }
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.info("Hello Job执行时间: " + new Date());
  }


}
