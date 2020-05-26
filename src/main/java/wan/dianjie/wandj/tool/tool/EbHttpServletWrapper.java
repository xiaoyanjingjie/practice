package wan.dianjie.wandj.tool.tool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

/**
 * 2：由于inputStream只能被读取一次，这时需要将流中数据存储起来，以便后续使用，需要继承HttpServletRequestWrapper类。
 *
 * @author wan dianjie
 * @date 2020-03-05 21:51
 */
@Slf4j
public class EbHttpServletWrapper extends HttpServletRequestWrapper {
  private String encoding = "UTF-8";
  private byte[] requestBodyIniBytes;

  public EbHttpServletWrapper(HttpServletRequest request) throws IOException {
    super(request);
    ServletInputStream stream = request.getInputStream();
    String requestBody = StreamUtils.copyToString(stream, Charset.forName(encoding));
    requestBodyIniBytes = requestBody.getBytes(encoding);

  }



  @Override
  public ServletInputStream getInputStream() {
    final ByteArrayInputStream in;
    in = new ByteArrayInputStream(requestBodyIniBytes);

    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener readListener) {

      }

      @Override
      public int read() throws IOException {

        return in.read();
      }
    };
  }
}
