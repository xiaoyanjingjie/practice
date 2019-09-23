package wan.dianjie.wandj.entidy;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * es
 *
 * @author wan dianjie
 * @date 2019-09-22 22:41
 */
//indexName索引名称 可以理解为数据库名 必须为小写 不然会报org.elasticsearch.indices.InvalidIndexNameException异常
//type类型 可以理解为表名
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "testgoods",type = "goods")
public class GoodsInfo implements Serializable {
  private Long id;
  private String name;
  private String description;


}
