package br.com.bomrastreio.api.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API for the dashboards.
 */
@RestController
@RequestMapping("/v1/dashboards")
public class DashboardApi {

  @Autowired
  private DashboardService dashboardService;

  /**
   * Gets the default dashboard.
   *
   * @return Default dashboard
   */
  @GetMapping("/DEFAULT")
  public DefaultDashboard summarize() {
    return dashboardService.summarize();
  }

}
