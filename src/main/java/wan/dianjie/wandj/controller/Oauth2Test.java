package wan.dianjie.wandj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * oauth 密码模式 练习
 *
 * @author wan dianjie
 * @date 2019-07-10 20:45
 */
@Slf4j
@RequestMapping("/oauth")
@RestController
public class Oauth2Test {
  @GetMapping("/product/{id}")
  public String getProduct(@PathVariable String id) {
    //for debug
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return "product id : " + id;
  }
}
