package wan.dianjie.wandj.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wan.dianjie.wandj.service.OrderQueryService;

/**
 * @author wan dianjie
 * @date 2020-05-11 18:31
 */
@Slf4j
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

  @Autowired
  private OrderQueryService orderQueryService;

  @GetMapping
  public String queryOrder3(@RequestParam("orderId") String orderId) {
    log.info("");
    String s =  orderQueryService.queryOrderInfo2(orderId);
    return s;
  }




}
