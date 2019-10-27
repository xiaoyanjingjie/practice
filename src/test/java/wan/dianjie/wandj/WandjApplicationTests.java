package wan.dianjie.wandj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;
import wan.dianjie.wandj.jobs.NewJob;
import wan.dianjie.wandj.service.quartz.BaseJob;

@EnableScheduling
@RunWith(SpringRunner.class)
@SpringBootTest
public class WandjApplicationTests{

  @Test
  public void kk(){
    Class<?> class1 = null;
    System.out.println("11111");
//    try {
//      class1 = Class.forName("wan.dianjie.wandj.jobs.NewJob");
//      System.out.println(class1.newInstance());
//    } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//    } catch (IllegalAccessException e) {
//      e.printStackTrace();
//    } catch (InstantiationException e) {
//      e.printStackTrace();
//    }

  }

}
