package wan.dianjie.wandj;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * java单例模式返回数据库连接
 *
 * @author wan dianjie
 * @date 2019-12-12 17:40
 */
public class DBHelper {
  private static final String driver = "com.mysql.jdbc.Driver";//数据库驱动
  private static final String url = "jdbc:mysql://localhost:3306/gumysql?useUnicon=true&characterEncoding=UTF-8";
  private static final String username = "root";
  private static final String password = "root";

  static
  {
    try
    {
      Class.forName(driver);
    }
    catch (Exception e)
    {

      e.printStackTrace();
    }
  }

  private static Connection conn = null;
  //单例模式返回数据库连接
  public static Connection getConnection() throws Exception
  {
    if(conn == null)
    {
      conn = DriverManager.getConnection(url, username, password);
      return conn;
    }
    else
    {
      return conn;
    }
  }
}
