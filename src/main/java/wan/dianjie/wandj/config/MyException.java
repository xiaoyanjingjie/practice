package wan.dianjie.wandj.config;

/**
 * 自定义异常
 *
 * @author wan dianjie
 * @date 2020-01-07 10:28
 */
public class MyException extends RuntimeException {
  private String message;

  public MyException() {
    super();
  }

  public MyException(String msg) {
    super(msg);
  }


  public MyException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;

  }
}
