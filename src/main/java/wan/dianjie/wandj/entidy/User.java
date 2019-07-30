package wan.dianjie.wandj.entidy;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wan dianjie
 * @date 2019-06-04 15:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_s_user")
public class User extends Model<User> {
  private String id;
  private String email;
}
