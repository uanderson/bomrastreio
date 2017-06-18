package br.com.bomrastreio.api.object;

import br.com.bomrastreio.api.correios.trace.TraceStatus;
import br.com.bomrastreio.api.tenant.TenantableEntity;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "object")

public class TrackingObject extends TenantableEntity {

  @Id
  private String id;
  private String code;
  private Instant eta;
  private String addressee;
  private String note;

  @Enumerated(EnumType.STRING)
  private ObjectStatus status;

  @Basic(fetch = FetchType.LAZY)
  private String json;

  protected TrackingObject() {
  }

  private TrackingObject(Builder builder) {
    this.id = builder.id;
    this.code = builder.code;
    this.eta = builder.eta;
    this.addressee = builder.addressee;
    this.note = builder.note;
    this.status = ObjectStatus.PENDING;
    this.json = "";
  }

  void updateEta(Instant eta) {
    Preconditions.checkNotNull(eta, "ETA cannot be null");
    this.eta = eta;
  }

  void updateAddressee(String addressee) {
    Preconditions.checkNotNull(eta, "Addressee cannot be null");
    this.addressee = addressee;
  }

  void updateNote(String note) {
    Preconditions.checkNotNull(note, "Note cannot be null");
    this.note = note;
  }

  void attachJson(String json) {
    Preconditions.checkNotNull(json, "JSON cannot be null");
    this.json = json;
  }

  boolean updateStatus(TraceStatus traceStatus) {
    ObjectStatus targetStatus = traceStatus.getObjectStatus();

    if (isDelayed(traceStatus)) {
      targetStatus = ObjectStatus.DELAYED;
    }

    if (this.status.equals(targetStatus)) {
      return false;
    } else {
      this.status = targetStatus;
      return true;
    }
  }

  boolean isDelayed(TraceStatus traceStatus) {
    Preconditions.checkNotNull(traceStatus, "Trace status cannot be null");
    ObjectStatus targetStatus = traceStatus.getObjectStatus();

    if (ObjectStatus.ACTION_REQUIRED.equals(targetStatus)
      || ObjectStatus.DELAYED.equals(targetStatus)
      || ObjectStatus.DELIVERED.equals(targetStatus)
      || ObjectStatus.LOSS.equals(targetStatus)) {
      return false;
    }

    if (ObjectStatus.IN_TRANSIT.equals(targetStatus)
      && (TraceStatus.BDE_66.equals(traceStatus)
        || TraceStatus.BDI_66.equals(traceStatus)
        || TraceStatus.BDR_66.equals(traceStatus))) {
      return false;
    }

    LocalDate etaDate = this.eta
      .atZone(ZoneId.systemDefault())
      .toLocalDate();

    return LocalDate.now().isAfter(etaDate);
  }

  public boolean canNotifyByMail() {
    return !Objects.equals(status, ObjectStatus.UNDEFINED)
      && !Objects.equals(status, ObjectStatus.PENDING);
  }

  public boolean canNotifyByWebSocket() {
    return !Objects.equals(status, ObjectStatus.UNDEFINED)
      && !Objects.equals(status, ObjectStatus.PENDING);
  }

  public String getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public Instant getEta() {
    return eta;
  }

  public String getAddressee() {
    return addressee;
  }

  public String getNote() {
    return note;
  }

  public ObjectStatus getStatus() {
    return status;
  }

  public String getJson() {
    return json;
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

    if (!(other instanceof TrackingObject)) {
      return false;
    }

    TrackingObject that = (TrackingObject) other;
    return Objects.equals(id, that.id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("code", code)
      .append("eta", eta)
      .append("addressee", addressee)
      .append("status", status)
      .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    @Id
    private String id;
    private String code;
    private Instant eta;
    private String addressee;
    private String note = "";
    private String json = "";

    public Builder withRandomId() {
      this.id = UUID.randomUUID().toString();
      return this;
    }

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withCode(String code) {
      this.code = code;
      return this;
    }

    public Builder withEta(Instant eta) {
      this.eta = eta;
      return this;
    }

    public Builder withAddressee(String addressee) {
      this.addressee = addressee;
      return this;
    }

    public Builder withNote(String note) {
      this.note = note;
      return this;
    }

    public Builder withJson(String json) {
      this.json = json;
      return this;
    }

    public TrackingObject build() {
      Preconditions.checkNotNull(id, "ID cannot be null");
      Preconditions.checkNotNull(code, "Code cannot be null");
      Preconditions.checkNotNull(eta, "ETA cannot be null");
      Preconditions.checkNotNull(addressee, "Addressee cannot be null");
      Preconditions.checkNotNull(note, "Note cannot be null");
      Preconditions.checkNotNull(json, "Json cannot be null");

      return new TrackingObject(this);
    }
  }

}
