package br.com.bomrastreio.api.object;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.time.Instant;

import static br.com.bomrastreio.api.support.validation.ValidationMessage.MAX_LENGTH;
import static br.com.bomrastreio.api.support.validation.ValidationMessage.REQUIRED;

public class ObjectResource {

  private String id;

  @NotBlank(message = REQUIRED)
  @Length(max = 13, message = MAX_LENGTH)
  private String code;

  @NotNull(message = REQUIRED)
  private Instant eta;

  @NotBlank(message = REQUIRED)
  @Length(max = 255, message = MAX_LENGTH)
  private String addressee;
  private ObjectStatus status;

  @Length(max = 1000, message = MAX_LENGTH)
  private String note;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Instant getEta() {
    return eta;
  }

  public void setEta(Instant eta) {
    this.eta = eta;
  }

  public String getAddressee() {
    return addressee;
  }

  public void setAddressee(String addressee) {
    this.addressee = addressee;
  }

  public ObjectStatus getStatus() {
    return status;
  }

  public void setStatus(ObjectStatus status) {
    this.status = status;
  }

  public String getNote() {
    return MoreObjects.firstNonNull(note, "");
  }

  public void setNote(String note) {
    this.note = note;
  }
}
