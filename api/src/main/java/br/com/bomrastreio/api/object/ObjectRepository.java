package br.com.bomrastreio.api.object;

import br.com.bomrastreio.api.dashboard.SimpleStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ObjectRepository
  extends JpaRepository<TrackingObject, String>, JpaSpecificationExecutor<TrackingObject> {

  Optional<TrackingObject> findByCode(String code);

  /**
   * Find all traceable tenants independently.
   *
   * @return Tenants ids
   */
  @Query(
    nativeQuery = true,
    value = "select distinct tenant from object where status <> 'DELIVERED'"
  )
  Set<String> findTraceableTenants();

  /**
   * Summarize statistics for the {@link TrackingObject}.
   *
   * @param tenant Current tenant id
   * @return Statistics from the objects
   */
  @Query(
    nativeQuery = true,
    value = "select 'TOTAL' as name, count(*) count from object where tenant = ?1 union " +
      "select status as name, count(*) count from object where tenant = ?1 group by status"
  )
  List<SimpleStatistic> collectStatistics(String tenant);

  /**
   * Find all objects existent in the references list.
   *
   * @param references List with codes
   * @return A list of objects
   */
  @Query("select o from TrackingObject o where o.code in ?1")
  List<?> findByCodeIn(Set<String> references);
}
