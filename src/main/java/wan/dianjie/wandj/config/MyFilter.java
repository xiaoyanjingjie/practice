package wan.dianjie.wandj.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 配置过滤器
 *
 * @author wan dianjie
 * @date 2019-07-30 20:05
 */
@Slf4j
public class MyFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    System.out.println("================================================"+servletRequest.getServletContext());
    log.info("================================================进入过滤器==============================================================================");
    HttpServletRequest hrequest = (HttpServletRequest)servletRequest;
    HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
    if(hrequest.getRequestURI().indexOf("/index") != -1 ||
        hrequest.getRequestURI().indexOf("/oauth") != -1 ||
        hrequest.getRequestURI().indexOf("/login") != -1 ||
        hrequest.getRequestURI().indexOf("/job") != -1

    ) {
      filterChain.doFilter(servletRequest, servletResponse);
    }else {
      wrapper.sendRedirect("/login");
    }
  }
  @Override
  public void destroy() {
  }
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }
}
