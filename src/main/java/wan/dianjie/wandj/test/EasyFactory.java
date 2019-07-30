package wan.dianjie.wandj.test;

import org.springframework.beans.factory.annotation.Autowired;
import wan.dianjie.wandj.mapper.UserMapper;

/**
 * 一个简单的操作工厂
 *
 * @author wan dianjie
 * @date 2019-06-20 10:44
 */
public class EasyFactory {
//  @Autowired
//  Operation operation;

  public Operation createOperation(String name){
    Operation operation = null;
    switch(name) {
      case "+":
        operation = new Add();
        break;
//      case "-":operation = new
    }

    return operation;
  }

}
