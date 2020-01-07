package wan.dianjie.wandj.config;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wan dianjie
 * @date 2020-01-07 11:14
 */
@Component
//@Order(1)
//@WebFilter(filterName = "MyTestFilter",urlPatterns = {"*.html"})
public class MyTestFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
