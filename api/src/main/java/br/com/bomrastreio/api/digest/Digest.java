package br.com.bomrastreio.api.digest;

import br.com.bomrastreio.api.tenant.TenantableEntity;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "digest")
public class Digest extends TenantableEntity {

  @Id
  private String id;

  @Enumerated(EnumType.STRING)
  private DigestType type;
  private String reference;

  @Temporal(TemporalType.DATE)
  private LocalDate date;

  protected Digest() {
  }

  private Digest(Builder builder) {
    this.id = builder.id;
    this.type = builder.type;
    this.reference = builder.reference;
    this.date = LocalDate.now();
  }

  public String getId() {
    return id;
  }

  public DigestType getType() {
    return type;
  }

  public String getReference() {
    return reference;
  }

  public LocalDate getDate() {
    return date;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Digest)) {
      return false;
    }

    Digest that = (Digest) other;
    return Objects.equals(id, that.id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("type", type)
      .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String id;
    private DigestType type;
    private String reference;

    public Builder withRandomId() {
      this.id = UUID.randomUUID().toString();
      return this;
    }

    public Builder withType(DigestType type) {
      this.type = type;
      return this;
    }

    public Builder withReference(String reference) {
      this.reference = reference;
      return this;
    }

    public Digest build() {
      Preconditions.checkNotNull(id, "ID cannot be null");
      Preconditions.checkNotNull(type, "Type cannot be null");
      Preconditions.checkNotNull(reference, "Reference cannot be null");

      return new Digest(this);
    }
  }

}
