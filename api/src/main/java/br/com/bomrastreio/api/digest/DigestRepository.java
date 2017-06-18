package br.com.bomrastreio.api.digest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DigestRepository extends JpaRepository<Digest, String>, JpaSpecificationExecutor<Digest> {

  /**
   * Find all unique tenants.
   *
   * @return Tenants ids
   */
  @Query(
    nativeQuery = true,
    value = "select distinct tenant from digest"
  )
  Set<String> findDeliverableTenants();


}
