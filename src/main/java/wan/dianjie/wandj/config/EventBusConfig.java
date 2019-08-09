package wan.dianjie.wandj.config;

import static java.util.concurrent.Executors.*;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;


/**
 * 事件总线
 * https://www.jianshu.com/p/4efbfdc01cf6
 * @author wan dianjie
 * @date 2019-08-09 15:26
 */
@Component
public class EventBusConfig {

  @Autowired
  private ApplicationContext context;

  @Bean
  @ConditionalOnMissingBean(AsyncEventBus.class)
  AsyncEventBus createEventBus() {
    // 创建一个核心3线程，最大10线程的线程池，配置DiscardPolicy策略，抛弃当前任务
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),new ThreadPoolExecutor.DiscardPolicy());
    //newFixedThreadPool(5)
    AsyncEventBus eventBus = new AsyncEventBus(threadPoolExecutor);
    Reflections reflections = new Reflections("wan.dianjie.wandj", new MethodAnnotationsScanner());
    Set<Method> methods = reflections.getMethodsAnnotatedWith(Subscribe.class);
    if (null != methods ) {
      for(Method method : methods) {
        try {
          eventBus.register(context.getBean(method.getDeclaringClass()));
        }catch (Exception e ) {
          //register subscribe class error
        }
      }
    }

    return eventBus;
  }

//  @Bean
//  @ConditionalOnMissingBean(EventBus.class)
//  EventBus createEventBus() {
//    EventBus eventBus = new EventBus();
//    Reflections reflections = new Reflections("wan.xxx", new MethodAnnotationsScanner());
//    Set<Method> methods = reflections.getMethodsAnnotatedWith(Subscribe.class);
//    if (null != methods ) {
//      for(Method method : methods) {
//        try {
//          eventBus.register(context.getBean(method.getDeclaringClass()));
//        }catch (Exception e ) {
//          //register subscribe class error
//        }
//      }
//    }
//
//    return eventBus;
//  }
}