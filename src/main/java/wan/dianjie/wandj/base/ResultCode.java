package wan.dianjie.wandj.base;

/**
 * 700*5-750*5 700* -- 通用错误代码 750*-- 用户中心错误编码 749*-- 平台管理中心错误代码 748*-- 签署 747*--消息中心 701*-- 认证中心 746*--DocHandle
 *
 * @author zhang xu
 * @date 2018/3/5.
 */
public interface ResultCode {

  /**
   * 操作代码
   *
   * @return 错误编码
   */
  int code();

  /**
   * 提示信息
   *
   * @return 错误信息
   */
  String message();

}
