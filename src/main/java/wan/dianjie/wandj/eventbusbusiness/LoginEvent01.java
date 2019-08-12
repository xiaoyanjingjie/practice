package wan.dianjie.wandj.eventbusbusiness;


import wan.dianjie.wandj.tool.eventbus.Event;

/**
 * 定义事件  这里以登入为例
 *
 * @author wan dianjie
 * @date 2019-08-09 15:42
 */
public class LoginEvent01 implements Event<LoginMsg> {

  private LoginMsg loginMsg;

  public LoginEvent01(LoginMsg loginMsg) {
    this.loginMsg = loginMsg;
  }

  @Override
  public LoginMsg getContent() {
    return this.loginMsg;
  }
}
