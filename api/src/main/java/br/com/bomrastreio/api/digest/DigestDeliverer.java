package br.com.bomrastreio.api.digest;

import br.com.bomrastreio.api.notification.RetryableMailSender;
import br.com.bomrastreio.api.tenant.TenantHolder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DigestDeliverer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DigestDeliverer.class);

  private final Environment environment;
  private final Configuration configuration;
  private final DigestService digestService;
  private final RetryableMailSender retryableMailSender;

  @Autowired
  public DigestDeliverer(
    Environment environment, Configuration configuration, DigestService digestService, RetryableMailSender retryableMailSender
  ) {
    this.environment = environment;
    this.configuration = configuration;
    this.digestService = digestService;
    this.retryableMailSender = retryableMailSender;
  }

  /**
   * Find all tenants and start deliver.
   */
  public void deliver() {
    digestService.findDeliverableTenants()
      .forEach(this::deliverBy);
  }

  /**
   * Define the current tenant and deliver the mail per
   * digest type. Each type has a directory where there is a
   * default template. Into this template, all the information
   * from digest will be filled.
   *
   * @param tenantId Current tenant id
   */
  private void deliverBy(String tenantId) {
    try {
      TenantHolder.set(tenantId);
      Map<DigestType, List<Digest>> group = digestService.findDeliverables()
        .stream().collect(Collectors.groupingBy(Digest::getType));

      group.forEach((type, digests) -> {
        List<?> references = digestService.findReferences(type, digests);
        retryableMailSender.send(createPreparator(tenantId, type, references));
      });

    } finally {
      TenantHolder.remove();
    }
  }

  /**
   * Create a preparator to prepare message for delivery.
   *
   * @param type       The digests type
   * @param references Digests to be delivered
   * @param tenant     Current tenant id
   * @return A mime message preparator for digest
   */
  private MimeMessagePreparator createPreparator(String tenant, DigestType type, List<?> references) {
    return mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
      messageHelper.setSubject("Eventos diário");
      messageHelper.setTo(tenant);
      messageHelper.setFrom(
        environment.getRequiredProperty("app.mail.default-sender"),
        environment.getRequiredProperty("app.mail.default-personal")
      );

      Map<String, Object> model = new HashMap<String, Object>() {{
        put("references", references);
        put("referencesCount", references.size());
      }};

      messageHelper.setText(createContent(type, model), true);
    };
  }

  /**
   * Apply the values to the template.
   *
   * @param type  Type of the digests
   * @param model Model with the data to be processed
   * @return The filled template string
   */
  private String createContent(DigestType type, Map<String, Object> model) {
    try {
      String typeName = StringUtils.lowerCase(type.name());
      Template template = configuration.getTemplate("/" + typeName + "/digest/default.ftl");
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    } catch (Exception ex) {
      LOGGER.error("Exception while processing template", ex);
      throw new IllegalStateException("Exception while processing template");
    }
  }

}
