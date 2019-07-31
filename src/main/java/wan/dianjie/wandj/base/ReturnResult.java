package wan.dianjie.wandj.base;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 响应信息主体
 *
 * @author meng ran
 * @date 2018-10-08 10:52
 */
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class ReturnResult<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  private int status = 200;

  @Getter
  @Setter
  private String message = "success";

  @Getter
  @Setter
  private T data;

  public ReturnResult() {
    super();
  }

  public ReturnResult(T data) {
    super();
    this.data = data;
  }

  public ReturnResult(ResultCode resultCode) {
    super();
    this.status = resultCode.code();
    this.message = resultCode.message();
  }

  public ReturnResult(T data, String message) {
    super();
    this.data = data;
    this.message = message;
  }

  public ReturnResult(Throwable e) {
    super();
    this.message = e.getMessage();
    this.status = -1;
  }

  public ReturnResult(int status, T data, String message) {
    super();
    this.data = data;
    this.message = message;
    this.status = status;
  }

  public ReturnResult(int status, String message) {
    super();
    this.message = message;
    this.status = status;
  }
}
