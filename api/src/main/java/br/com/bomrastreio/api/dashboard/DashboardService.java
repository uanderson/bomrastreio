package br.com.bomrastreio.api.dashboard;

import br.com.bomrastreio.api.object.ObjectRepository;
import br.com.bomrastreio.api.object.ObjectStatus;
import br.com.bomrastreio.api.security.KeycloakHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

  private final ObjectRepository objectRepository;

  @Autowired
  public DashboardService(ObjectRepository objectRepository) {
    this.objectRepository = objectRepository;
  }

  /**
   * Summarize the data to the default dashboard.
   *
   * @return Default dashboard
   */
  public DefaultDashboard summarize() {
    String currentTenant = getUserName();
    Map<String, Long> statistics = new HashMap<>();

    objectRepository.collectStatistics(currentTenant)
      .forEach(statistic -> statistics.put(statistic.getName(), statistic.getValue()));

    return DefaultDashboard.builder()
      .withTotalCount(statistics.getOrDefault("TOTAL", 0L))
      .withDeliveredCount(statistics.getOrDefault(ObjectStatus.DELIVERED.name(), 0L))
      .withDelayedCount(statistics.getOrDefault(ObjectStatus.DELAYED.name(), 0L))
      .withLossCount(statistics.getOrDefault(ObjectStatus.LOSS.name(), 0L))
      .build();
  }

  public String getUserName() {
    return KeycloakHolder.getUsername();
  }

}
