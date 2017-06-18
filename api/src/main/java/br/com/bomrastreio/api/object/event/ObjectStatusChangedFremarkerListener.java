package br.com.bomrastreio.api.object.event;

import br.com.bomrastreio.api.object.ObjectStatus;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

public abstract class ObjectStatusChangedFremarkerListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectStatusChangedFremarkerListener.class);

  private final Configuration configuration;

  public ObjectStatusChangedFremarkerListener(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * Create the content based on the template.
   *
   * @param status Current status
   * @param prefix Prefix classpath folder
   * @param model  Model to be applied to the template
   * @return Transformed template
   */
  protected String createContent(String prefix, ObjectStatus status, Map<String, Object> model) {
    try {
      String statusName = StringUtils.lowerCase(status.name());
      Template template = configuration.getTemplate(prefix + "/" + statusName + ".ftl");
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    } catch (Exception ex) {
      LOGGER.error("Exception while processing template", ex);
      throw new IllegalStateException("Exception while processing template");
    }
  }
}
