package wan.dianjie.wandj;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import sun.lwawt.macosx.CSystemTray;
import wan.dianjie.wandj.service.quartz.BaseJob;

/**
 * @author wan dianjie
 * @date 2019-10-27 14:53
 */
public class Synchronizedtest {

  class Allocator {
    private List<Object> als =
        new ArrayList<>();
    // 一次性申请所有资源
    synchronized boolean apply(
        Object from, Object to){
      if(als.contains(from) ||
          als.contains(to)){
        return false;
      } else {
        als.add(from);
        als.add(to);
      }
      return true;
    }
    // 归还资源
    synchronized void free(
        Object from, Object to){
      als.remove(from);
      als.remove(to);
    }
  }

  class Account {
    // actr应该为单例
    private Allocator actr;
    private int balance;
    // 转账
    void transfer(Account target, int amt){
      // 一次性申请转出账户和转入账户，直到成功
      while(!actr.apply(this, target))
        ;
      try{
        // 锁定转出账户
        synchronized(this){
          // 锁定转入账户
          synchronized(target){
            if (this.balance > amt){
              this.balance -= amt;
              target.balance += amt;
            }
          }
        }
      } finally {
        actr.free(this, target);
      }
    }
  }
//  }

  public static void main(String[] args) {
    class Allocator {
      private List<Object> als =  new ArrayList<>();
      // 一次性申请所有资源
      synchronized boolean apply(
          Object from, Object to){
        return true;
      }
      // 归还资源
      synchronized void free(
          Object from, Object to){
        als.remove(from);
        als.remove(to);
      }
    }
    //--------------------------------
    Allocator l = new Allocator();
   while (l.apply(new Object(),new Object()))
     ;
   System.out.println("ppppp");
    //--------------------------------
    //--------------------------------

//    @Data
//    class Account {
//      private int balance;
//      private String name;
//      private int id;
//      // 转账
//      void transfer(Account target, int amt){
//        System.out.println(target);
//        System.out.println(this);
//        // 锁定转出账户
//        synchronized(this){
//          // 锁定转入账户
//          synchronized(target){
//            if (this.balance > amt) {
//              System.out.println("前 this:"+ this.balance);
//              System.out.println( "前 terget:"+target.balance);
//              this.balance -= amt;
//              System.out.println("中 this:"+ this.balance);
//              System.out.println("中 this:"+ target.balance);
//              target.balance += amt;
//              System.out.println("后 this:"+ this.balance);
//              System.out.println( "后 this:"+target.balance);
//            }
//          }
//        }
//      }
//
//      int getBalanceThis(){
//        synchronized(this){
//          // 锁定转入账户
//            return this.balance;
//          }
//      }
//      int getTargertBalance(Account target){
//        synchronized(target) {
//          return target.getBalance();
//        }
//      }
//    }
//    //A->B 转账100
//    Account accountA = new Account();
//    Account accountB = new Account();
//    accountB.setBalance(200);
//    accountB.setId(2);
//    accountB.setName("lisi");
//    Thread test6 = new Thread(new Runnable() {
//      @Override
//      public void run() {
//        accountA.setBalance(200);
//        accountA.setId(1);
//        accountA.setName("zhangsan");
//        accountA.transfer(accountB,100);
//      }
//    },"test6");
//    System.out.println("a-b:"+accountA.getBalanceThis());
//    System.out.println( "a-b:"+accountA.getTargertBalance(accountB));
//    test6.start();
//    try {
//      test6.join();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    System.out.println("end a-b:"+accountA.getBalanceThis());
//    System.out.println( "end a-b:"+accountA.getTargertBalance(accountB));
//
//
//   //B->A 转账100
//    Thread test7 = new Thread(new Runnable() {
//      @Override
//      public void run() {
//        accountB.transfer(accountA,100);
//      }
//    },"test7");
//    System.out.println("b-a:"+accountB.getBalanceThis());
//    System.out.println( "b-a:"+accountB.getTargertBalance(accountA));
//    test7.start();
//    try {
//      test7.join();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    System.out.println("end b-a:"+accountB.getBalanceThis());
//    System.out.println( "end b-a"+accountB.getTargertBalance(accountA));
    //--------------------------------
    //--------------------------------
//     class Account {
//      // 锁：保护账户余额
//      private final Object balLock
//          = new Object();
//      // 账户余额
//      private Integer balance;
//      // 锁：保护账户密码
//      private final Object pwLock
//          = new Object();
//      // 账户密码
//      private String password= "000000";
//
//      // 取款
//      void withdraw(Integer amt) {
//        synchronized(balLock) {
//          if (this.balance > amt){
//            this.balance -= amt;
//          }
//        }
//      }
//      // 查看余额
//      Integer getBalance() {
//        synchronized(balLock) {
//          return balance;
//        }
//      }
//
//      // 更改密码
//      void updatePassword(String pw){
//        synchronized(pwLock) {
//          try {
//            Thread.sleep(2000);
//            System.out.println(Thread.currentThread().getName());
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
//          this.password = pw;
//        }
//      }
//      // 查看密码
//      String getPassword() {
//        synchronized(pwLock) {
//          return password;
//        }
//      }
//    }
//    Account account = new Account();
//    Account account1 = new Account();
//
//    Thread test1 = new Thread(new Runnable() {
//      public void run() {
//        account.updatePassword("123456");
//      }
//    }, "test1");
//    Thread test2 = new Thread(new Runnable() {
//      public void run() {
//        account.updatePassword("234567");
//      }
//    }, "test2");
//    test1.start();
//    test2.start();
//    System.out.println(account.getPassword());
//    System.out.println(account.getPassword());
//    System.out.println(account1.getPassword());


//    Thread test3 = new Thread(new Runnable() {
//      public void run() {
//        AccountStatic.updatePassword("123456");
//      }
//    }, "test3");
//    Thread test4 = new Thread(new Runnable() {
//      public void run() {
//        AccountStatic.updatePassword("234567");
//      }
//    }, "test4");

//    Thread test5 = new Thread(new Runnable() {
//      public void run() {
//          System.out.println("获取pw"+AccountStatic.getPassword()+" 获取pw:"+ LocalDateTime.now());
//      }
//    }, "test5");
//    test3.start();
//    test5.start();
//--------------------------------
//--------------------------------
//    class AccountBalance {
//      private int balance = 200;
//      // 转账
//      void transfer(AccountBalance target, int amt){
//        synchronized(AccountBalance.class) {
//          if (this.balance > amt) {
//            this.balance -= amt;
//            target.balance += amt;
//          }
//        }
//      }
//    }
//    AccountBalance accountBalance= new AccountBalance();
//    Thread test5 = new Thread(new Runnable() {
//      public void run() {
//         accountBalance.transfer(accountBalance,100);
//      }
//    }, "test5");
//    test5.start();
//--------------------------------


  }

  public static BaseJob getClass(String classname) throws Exception {
    Class<?> class1 = Class.forName(classname);
    return (BaseJob) class1.newInstance();
  }




}
