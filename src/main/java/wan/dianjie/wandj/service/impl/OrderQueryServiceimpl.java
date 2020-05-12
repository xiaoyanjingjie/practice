package wan.dianjie.wandj.service.impl;

import static sun.security.x509.X509CertInfo.KEY;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import wan.dianjie.wandj.service.OrderQueryService;

/**
 * @author wan dianjie
 * @date 2020-05-11 18:47
 */
@Service
public class OrderQueryServiceimpl implements OrderQueryService {

  private static final String KEY = "queryGoodsInfo2";
  /**
   * 订单查询接口, 使用Sentinel注解实现限流
   *
   * @param orderId
   * @return
   */
  @Override
  @SentinelResource(value = KEY, blockHandler = "handleFlowQpsException",
      fallback = "queryOrderInfo2Fallback")
  public String queryOrderInfo2( String orderId) {

    // 模拟接口运行时抛出代码异常
    if ("000".equals(orderId)) {
      throw new RuntimeException();
    }

    System.out.println("获取订单信息:" + orderId);
    return "return OrderInfo :" + orderId;
  }

  /**
   * 订单查询接口抛出限流或降级时的处理逻辑
   *
   * 注意: 方法参数、返回值要与原函数保持一致
   * @return
   */
  public String handleFlowQpsException( String orderId,  BlockException e) {
    e.printStackTrace();
    return "handleFlowQpsException for queryOrderInfo2: " + orderId;
  }

  /**
   * 订单查询接口运行时抛出的异常提供fallback处理
   *
   * 注意: 方法参数、返回值要与原函数保持一致
   * @return
   */
  public String queryOrderInfo2Fallback( String orderId,  Throwable e) {
    return "fallback queryOrderInfo2: " + orderId;
  }

  @PostConstruct
  public void initDegradeRule() {
    List<DegradeRule> rules = new ArrayList<>();
    DegradeRule rule = new DegradeRule();
    rule.setResource(KEY);
    // 80s内调用接口出现异常次数超过5的时候, 进行熔断
    rule.setCount(5);
    rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
    rule.setTimeWindow(80);
    rules.add(rule);
    DegradeRuleManager.loadRules(rules);
  }


}
