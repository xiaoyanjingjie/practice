package wan.dianjie.wandj.mapper;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import wan.dianjie.wandj.entidy.GoodsInfo;

@Component
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo,Long> {
 void deleteByName(String name);
}