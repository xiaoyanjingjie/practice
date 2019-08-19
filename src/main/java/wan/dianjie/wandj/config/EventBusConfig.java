package wan.dianjie.wandj.config;

import static java.util.concurrent.Executors.*;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class EventBusConfig {

  @Autowired
  private ApplicationContext context;

  @Bean
  @ConditionalOnMissingBean(AsyncEventBus.class)
  AsyncEventBus createEventBus() {
    log.info("-----------------进入--AsyncEventBus-----------------------");
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
          eventBus.unregister(this);

        }
      }
    }

    return eventBus;
  }

//  @Bean
//  @ConditionalOnMissingBean(EventBus.class)
//  EventBus createEventBus1() {
//    log.info("-----------------进入--EventBus-----------------------");
//    EventBus eventBus = new EventBus();
//    Reflections reflections = new Reflections("wan.dianjie.wandj", new MethodAnnotationsScanner());
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

  /**
   *    @Override
   * 	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
   * 		// 查找方法带有@Subscribe注解的类，进行事件监听注册
   * 		for (Method m : bean.getClass().getMethods()) {
   * 			if (m.isAnnotationPresent(Subscribe.class)) {
   * 				log.info("register bean as subscriber by @Subscribe annotation.");
   * 				this.eventBus.register(bean);
   * 				this.asyncEventBus.register(bean);
   * 				break;
   * 			}
   * 		}
   */
}