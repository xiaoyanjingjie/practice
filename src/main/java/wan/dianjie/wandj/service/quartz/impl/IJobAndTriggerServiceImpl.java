package wan.dianjie.wandj.service.quartz.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wan.dianjie.wandj.entidy.JobAndTrigger;
import wan.dianjie.wandj.mapper.JobAndTriggerMapper;
import wan.dianjie.wandj.service.quartz.IJobAndTriggerService;

/**
 * Created by haoxy on 2018/9/28.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
public class IJobAndTriggerServiceImpl implements IJobAndTriggerService {

    @Autowired
    private JobAndTriggerMapper jobAndTriggerMapper;

    @Override
    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
        PageInfo<JobAndTrigger> page = new PageInfo<>(list);
        return page;
    }
}
