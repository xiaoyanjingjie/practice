package wan.dianjie.wandj;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;

/**
 * @author wan dianjie
 * @date 2019-12-12 17:44
 */
public class wan {

  public static void main(String[] args) {
    String templateField = null;
    JSONArray templateArr = JSONArray.parseArray(templateField);
    if(ArrayUtil.isNotEmpty(templateArr)){
      System.out.println(templateArr.size());

    }

  }
}
