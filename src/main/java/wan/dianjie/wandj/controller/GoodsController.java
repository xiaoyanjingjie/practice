package wan.dianjie.wandj.controller;

import java.util.List;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wan.dianjie.wandj.entidy.GoodsInfo;
import wan.dianjie.wandj.mapper.GoodsRepository;

/**
 * ES
 *
 * @author wan dianjie
 * @date 2019-09-22 22:44
 */
@RestController
@RequestMapping("/es")
public class GoodsController {

  @Autowired
  private GoodsRepository goodsRepository;

  //http://localhost:8888/save
  @GetMapping("/save")
  public String save(){
    GoodsInfo goodsInfo = new GoodsInfo(System.currentTimeMillis(),
        "商品"+System.currentTimeMillis(),"这是一个测试商品");
    goodsRepository.save(goodsInfo);
    return "success";
  }

  //http://localhost:8888/delete?id=1525415333329
  @GetMapping("/delete")
  public String delete(long id){
    goodsRepository.deleteById(id);
    return "success";
  }

  //http://localhost:8888/update?id=1525417362754&name=修改&description=修改
  @GetMapping("/update")
  public String update(long id,String name,String description){
    GoodsInfo goodsInfo = new GoodsInfo(id,
        name,description);
    goodsRepository.save(goodsInfo);
    return "success";
  }

  //http://localhost:8888/update?id=1525417362754&name=修改&description=修改
  @GetMapping("/getAll")
  public Iterable getAll(){
    return  goodsRepository.findAll();
  }

  @GetMapping("/deleteByName")
  public boolean deleteByName(String name){
    goodsRepository.deleteByName(name);
    return true;
  }

  //http://localhost:8888/getOne?id=1525417362754
//  @GetMapping("getOne")
//  public GoodsInfo getOne(long id){
//    GoodsInfo goodsInfo = goodsRepository.findOne(id);
//    return goodsInfo;
//  }


  //每页数量
  private Integer PAGESIZE=10;

  //http://localhost:8888/getGoodsList?query=商品
  //http://localhost:8888/getGoodsList?query=商品&pageNumber=1
  //根据关键字"商品"去查询列表，name或者description包含的都查询
//  @GetMapping("getGoodsList")
//  public List<GoodsInfo> getList(Integer pageNumber,String query){
//    if(pageNumber==null) {
//      pageNumber = 0;
//    }
//    //es搜索默认第一页页码是0
//    SearchQuery searchQuery=getEntitySearchQuery(pageNumber,PAGESIZE,query);
//    Page<GoodsInfo> goodsPage = goodsRepository.search(searchQuery);
//    return goodsPage.getContent();
//  }


//  private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
//    FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//        .add(QueryBuilders.matchPhraseQuery("name", searchContent),
//            ScoreFunctionBuilders.weightFactorFunction(100))
//        .add(QueryBuilders.matchPhraseQuery("description", searchContent),
//            ScoreFunctionBuilders.weightFactorFunction(100))
//        //设置权重分 求和模式
//        .scoreMode("sum")
//        //设置权重分最低分
//        .setMinScore(10);
//
//    // 设置分页
//    Pageable pageable = new PageRequest(pageNumber, pageSize);
//    return new NativeSearchQueryBuilder()
//        .withPageable(pageable)
//        .withQuery(functionScoreQueryBuilder).build();
//  }

}