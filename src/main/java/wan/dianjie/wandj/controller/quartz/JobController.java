package wan.dianjie.wandj.controller.quartz;

import static org.quartz.DateBuilder.futureDate;

import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wan.dianjie.wandj.entidy.JobAndTrigger;
import wan.dianjie.wandj.entidy.JobInfo;
import wan.dianjie.wandj.service.quartz.BaseJob;
import wan.dianjie.wandj.service.quartz.IJobAndTriggerService;
import wan.dianjie.wandj.tool.tool.DateUnit;

/**
 * @author wan dianjie
 * @date 2019-10-16 16:14
 */

@RestController
@RequestMapping(value = "job")
@Slf4j
public class JobController {

  @Autowired
  private IJobAndTriggerService iJobAndTriggerService;

  //加入Qulifier注解，通过名称注入bean
  @Autowired
  @Qualifier("Scheduler")
  private Scheduler scheduler;
  @Autowired
  private DateUnit dateUnit;


  /**
   * 添加任务
   *
   * @param jobInfo
   * @throws Exception
   */
  @PostMapping(value = "/addjob")
  public void addjob(@RequestBody JobInfo jobInfo) throws Exception {
    if ("".equals(jobInfo.getJobClassName()) || "".equals(jobInfo.getJobGroupName()) || "".equals(jobInfo.getCronExpression())) {
      return;
    }
    if (jobInfo.getTimeType() == null) {
      addCronJob(jobInfo);
      return;
    }
    addSimpleJob(jobInfo);
  }

  //CronTrigger
  public void addCronJob(JobInfo jobInfo) throws Exception {

    // 启动调度器
    scheduler.start();

    //构建job信息
    JobDetail jobDetail = JobBuilder.newJob(getClass(jobInfo.getJobClassName()).getClass()).
        withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
        .build();

    //表达式调度构建器(即任务执行的时间)
    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
    //按新的cronExpression表达式构建一个新的trigger
    CronTrigger trigger = TriggerBuilder.newTrigger().
        withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
        .withSchedule(scheduleBuilder)
        .build();

    try {
      scheduler.scheduleJob(jobDetail, trigger);

    } catch (SchedulerException e) {
      System.out.println("创建定时任务失败" + e);
      throw new Exception("创建定时任务失败");
    }
  }

  //Simple Trigger
  public void addSimpleJob(JobInfo jobInfo) throws Exception {

    // 启动调度器
    scheduler.start();

    //构建job信息
    JobDetail jobDetail = JobBuilder.newJob(getClass(jobInfo.getJobClassName()).getClass())
        .withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
        .build();

    DateBuilder.IntervalUnit verDate = dateUnit.verification(jobInfo.getTimeType());
    SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
        .withIdentity(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
        .startAt(futureDate(Integer.parseInt(jobInfo.getCronExpression()), verDate))
        .forJob(jobInfo.getJobClassName(), jobInfo.getJobGroupName())
        .build();

    try {
      scheduler.scheduleJob(jobDetail, simpleTrigger);


    } catch (SchedulerException e) {
      System.out.println("创建定时任务失败" + e);
      throw new Exception("创建定时任务失败");
    }
  }

  /**
   * 暂停任务
   *
   * @param jobClassName
   * @param jobGroupName
   * @throws Exception
   */
  @PostMapping(value = "/pausejob")
  public void pausejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
    jobPause(jobClassName, jobGroupName);
  }

  public void jobPause(String jobClassName, String jobGroupName) throws Exception {
    scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
  }

  /**
   * 恢复任务
   *
   * @param jobClassName
   * @param jobGroupName
   * @throws Exception
   */
  @PostMapping(value = "/resumejob")
  public void resumejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
    jobresume(jobClassName, jobGroupName);
  }

  public void jobresume(String jobClassName, String jobGroupName) throws Exception {
    scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
  }

  /**
   * 更新任务
   *
   * @param jobClassName
   * @param jobGroupName
   * @param cronExpression
   * @throws Exception
   */
  @PostMapping(value = "/reschedulejob")
  public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
      @RequestParam(value = "jobGroupName") String jobGroupName,
      @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
    jobreschedule(jobClassName, jobGroupName, cronExpression);
  }

  public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
      // 表达式调度构建器
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

      CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

      // 按新的cronExpression表达式重新构建trigger
      trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

      // 按新的trigger重新设置job执行
      scheduler.rescheduleJob(triggerKey, trigger);
    } catch (SchedulerException e) {
      System.out.println("更新定时任务失败" + e);
      throw new Exception("更新定时任务失败");
    }
  }

  /**
   * 删除任务
   * 删除操作前应该暂停该任务的触发器，并且停止该任务的执行
   *
   * @param jobClassName
   * @param jobGroupName
   * @throws Exception
   */
  @PostMapping(value = "/deletejob")
  public void deletejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
    jobdelete(jobClassName, jobGroupName);
  }

  public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
    scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
    scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
    scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
  }

  /**
   * 查询任务
   *
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping(value = "/queryjob")
  public Map<String, Object> queryjob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
    PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("JobAndTrigger", jobAndTrigger);
    map.put("number", jobAndTrigger.getTotal());
    return map;
  }

  /**
   * 根据类名称，通过反射得到该类，然后创建一个BaseJob的实例。
   * 由于NewJob和HelloJob都实现了BaseJob，
   * 所以这里不需要我们手动去判断。这里涉及到了一些java多态调用的机制
   *
   * @param classname
   * @return
   * @throws Exception
   */
  public static BaseJob getClass(String classname) throws Exception {
    Class<?> class1 = Class.forName(classname);
    return (BaseJob) class1.newInstance();
  }
}
