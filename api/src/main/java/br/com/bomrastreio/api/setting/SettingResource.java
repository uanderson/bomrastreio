package br.com.bomrastreio.api.setting;

import br.com.bomrastreio.api.support.validation.UpdateGroup;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static br.com.bomrastreio.api.support.validation.ValidationMessage.MAX_LENGTH;
import static br.com.bomrastreio.api.support.validation.ValidationMessage.REQUIRED;

public class SettingResource {

  private String id;

  @NotNull(message = REQUIRED)
  @Length(
    max = 255,
    message = MAX_LENGTH,
    groups = {
      UpdateGroup.class
    })
  private SettingKey key;

  @Length(
    max = 255,
    message = MAX_LENGTH)
  private String value;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SettingKey getKey() {
    return key;
  }

  public void setKey(SettingKey key) {
    this.key = key;
  }

  public String getValue() {
    return MoreObjects.firstNonNull(value, "");
  }

  public void setValue(String value) {
    this.value = value;
  }
}
