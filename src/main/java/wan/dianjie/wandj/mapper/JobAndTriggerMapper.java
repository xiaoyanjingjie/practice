package wan.dianjie.wandj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import wan.dianjie.wandj.entidy.JobAndTrigger;

/**
 * Created by haoxy on 2018/9/28.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Mapper
public interface JobAndTriggerMapper  extends BaseMapper<JobAndTrigger> {

    List<JobAndTrigger> getJobAndTriggerDetails();
}
