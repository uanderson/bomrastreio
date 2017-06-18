package br.com.bomrastreio.api.tenant;

import br.com.bomrastreio.api.audit.AuditableEntity;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Multitenant
@MappedSuperclass
@TenantDiscriminatorColumn(name = "tenant")
public class TenantableEntity extends AuditableEntity {

  @Column(name = "tenant", insertable = false, updatable = false)
  private String tenant;

  public String getTenant() {
    return tenant;
  }

}
