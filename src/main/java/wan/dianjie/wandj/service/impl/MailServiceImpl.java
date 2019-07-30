package wan.dianjie.wandj.service.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import wan.dianjie.wandj.service.MailService;

/**
 * @author wan dianjie
 * @date 2019-06-04 20:43
 */
@Service
public class MailServiceImpl implements MailService {
  @Value("${spring.mail.username}")
  private String from;

  //注入MailSender
  @Autowired
  private JavaMailSender mailSender;

  //发送邮件的模板引擎
  @Autowired
  private FreeMarkerConfigurer configurer;

  /**
   * @param params       发送邮件的主题对象 object
   * @param title        邮件标题
   * @param templateName 模板名称
   */
  @Override
  public void sendMessageMail(Object params, String title, String templateName) {

    try {

      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(from);
      helper.setTo(InternetAddress.parse("xxxxx@163.com"));//发送给谁
      helper.setSubject("【" + title + "-" + LocalDate.now() + " " + LocalTime.now().withNano(0) + "】");//邮件标题

      Map<String, Object> model = new HashMap<>();
      model.put("params", params);
      try {
        Template template = configurer.getConfiguration().getTemplate(templateName);
        try {
          String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

          helper.setText(text, true);
          mailSender.send(mimeMessage);
        } catch (TemplateException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}
