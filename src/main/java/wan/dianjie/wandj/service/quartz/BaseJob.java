package wan.dianjie.wandj.service.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 基础job接口
 *
 * @author wan dianjie
 * @date 2019-10-16 16:06
 */
public interface BaseJob extends Job {

  @Override
  void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
