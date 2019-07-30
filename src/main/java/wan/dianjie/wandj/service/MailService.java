package wan.dianjie.wandj.service;

/**
 * @author wan dianjie
 * @date 2019-06-04 20:45
 */
public interface MailService {
  /**
   * @param params       发送邮件的主题对象 object
   * @param title        邮件标题
   * @param templateName 模板名称
   */
   void sendMessageMail(Object params, String title, String templateName);
}
