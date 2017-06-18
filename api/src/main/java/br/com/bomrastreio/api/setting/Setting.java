package br.com.bomrastreio.api.setting;


import br.com.bomrastreio.api.tenant.TenantableEntity;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "setting")
public class Setting extends TenantableEntity {

  @Id
  private String id;

  @Enumerated(EnumType.STRING)
  @Column(name = "setting_key")
  private SettingKey key;
  private String value;

  protected Setting() {
  }

  private Setting(Builder builder) {
    this.id = builder.id;
    this.key = builder.key;
    this.value = builder.value;
  }

  public String getId() {
    return id;
  }

  public SettingKey getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public void updateValue(String value) {
    Preconditions.checkNotNull(value, "Value cannot be null");
    this.value = value;
  }

  public boolean asBoolean() {
    return BooleanUtils.toBoolean(value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Setting)) {
      return false;
    }

    Setting that = (Setting) other;
    return Objects.equals(id, that.id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("key", key)
      .append("value", value)
      .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String id;
    private SettingKey key;
    private String value = "";

    public Builder withRandomId() {
      this.id = UUID.randomUUID().toString();
      return this;
    }

    public Builder withKey(SettingKey key) {
      this.key = key;
      return this;
    }

    public Builder withValue(String value) {
      this.value = value;
      return this;
    }

    public Setting build() {
      Preconditions.checkNotNull(id, "ID cannot be null");
      Preconditions.checkNotNull(key, "Key cannot be null");
      Preconditions.checkNotNull(value, "Value cannot be null");

      return new Setting(this);
    }

  }

}
