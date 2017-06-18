package br.com.bomrastreio.api.audit;

import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  @LastModifiedBy
  @Column(name = "updated_by")
  private String updatedBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Date updatedAt;

  public String getCreatedBy() {
    return MoreObjects.firstNonNull(createdBy, "");
  }

  public Optional<Instant> getCreatedAt() {
    if (Objects.isNull(createdAt)) {
      return Optional.empty();
    }

    return Optional.of(createdAt.toInstant());
  }

  public String getUpdatedBy() {
    return MoreObjects.firstNonNull(updatedBy, "");
  }

  public Optional<Instant> getUpdatedAt() {
    if (Objects.isNull(updatedAt)) {
      return Optional.empty();
    }

    return Optional.of(updatedAt.toInstant());
  }
}
