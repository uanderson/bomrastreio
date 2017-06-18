package br.com.bomrastreio.api.tenant;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;

/**
 * Manages transaction with tenant ID capabilities.
 */
public class TenantTransactionManager extends JpaTransactionManager {

  private final TenantResolver tenantResolver;

  @Autowired
  public TenantTransactionManager(TenantResolver tenantResolver) {
    this.tenantResolver = tenantResolver;
  }

  /**
   * A transaction is started when spring finds a {@link Transactional}. At this
   * moment, this method will recover the tenant ID through a {@link TenantResolver}.
   * <p>
   * It's important how we open transaction, because it affects the way objects are
   * persisted, deleted or loaded.
   *
   * @param transaction Transacted object
   * @param definition  Transaction definition
   */
  @Override
  protected void doBegin(Object transaction, TransactionDefinition definition) {
    super.doBegin(transaction, definition);

    EntityManagerHolder entityManagerHolder = (EntityManagerHolder)
      TransactionSynchronizationManager.getResource(getEntityManagerFactory());

    EntityManager entityManager = entityManagerHolder.getEntityManager();
    entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantResolver.getTenant());
  }
}
