package wan.dianjie.wandj.dto;

import lombok.Data;

/**
 * 记录日志基类
 *
 * @author wan dianjie
 * @date 2019-08-21 14:28
 */
@Data
public class BaseLogVO {

  /**
   * 业务类型
   */
  private String bType;
  /**
   * 业务名称
   */
  private String bName;
  /**
   * 操作人name
   */
  private String opraName;
  /**
   * 操作人id
   */
  private String opraId;
}
