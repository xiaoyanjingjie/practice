package wan.dianjie.wandj.test;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 实现操作+的接口
 *
 * @author wan dianjie
 * @date 2019-06-20 10:41
 */
@Service
@AllArgsConstructor
public class Add implements Operation {

  @Override
  public double getResult(double numA, double numB) {
    return numA+numB;
  }
}
