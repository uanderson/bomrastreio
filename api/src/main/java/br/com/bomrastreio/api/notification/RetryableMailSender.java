package br.com.bomrastreio.api.notification;

import br.com.bomrastreio.api.configuration.RetryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class RetryableMailSender {

  private final RetryTemplate retryTemplate;
  private final JavaMailSender javaMailSender;

  @Autowired
  public RetryableMailSender(JavaMailSender javaMailSender, RetryTemplate retryTemplate) {
    this.javaMailSender = javaMailSender;
    this.retryTemplate = retryTemplate;
  }

  /**
   * Try to deliver the email, but if the server is down, keeps trying
   * during the time configured in the {@link RetryConfiguration}.
   *
   * @param preparator Prepare the e-mail to send
   */
  public void send(final MimeMessagePreparator preparator) {
    retryTemplate.execute(context -> {
      javaMailSender.send(preparator);
      return null;
    });
  }
}
