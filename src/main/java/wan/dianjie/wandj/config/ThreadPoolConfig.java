package wan.dianjie.wandj.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author wan dianjie
 * @date 2019-06-03 10:45
 */
@Configuration
@Data
public class ThreadPoolConfig {
  @Bean(name= "taskExecutorPool")
  public Executor taskExecutorPool(){
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(2);

    executor.setKeepAliveSeconds(200);
    executor.setThreadNamePrefix("taskExecutorWbswryxx-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(200);

    System.out.println("当前活动线程数："+ executor.getActiveCount());
    System.out.println("核心线程数："+ executor.getCorePoolSize());
    System.out.println("总线程数："+ executor.getPoolSize());
    System.out.println("最大线程池数量"+executor.getMaxPoolSize());
    System.out.println("活动线程的当前线程的线程组中的数量"+executor.getActiveCount());
    //System.out.println("线程处理队列长度"+executor.getThreadPoolExecutor().getQueue().size());
    return executor;
  }

}
