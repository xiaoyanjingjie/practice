package wan.dianjie.wandj;

import wan.dianjie.wandj.service.quartz.BaseJob;

/**
 * @author wan dianjie
 * @date 2019-10-27 14:53
 */
public class wan {

  public static void main(String[] args) {
    try {
    String name = "wan.dianjie.wandj.jobs.NewJob";
    System.out.println( getClass(name));
    }  catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static BaseJob getClass(String classname) throws Exception {
    Class<?> class1 = Class.forName(classname);
    return (BaseJob) class1.newInstance();
  }

}
