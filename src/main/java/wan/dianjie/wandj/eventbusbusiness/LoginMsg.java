package wan.dianjie.wandj.eventbusbusiness;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息体
 *
 * @author wan dianjie
 * @date 2019-08-09 15:44
 */
@AllArgsConstructor
@Data
public class LoginMsg {
  private String uid;
  private String mobile;
  private String ip;
  private String osVersion;
  private String deviceModel;
  private String deviceToken;
}
