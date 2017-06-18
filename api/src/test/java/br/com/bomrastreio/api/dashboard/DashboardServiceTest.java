package br.com.bomrastreio.api.dashboard;

import br.com.bomrastreio.api.object.ObjectService;
import br.com.bomrastreio.api.object.ObjectStatus;
import br.com.bomrastreio.api.object.TrackingObject;
import br.com.bomrastreio.api.tenant.TenantHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.UUID;

import static br.com.bomrastreio.api.object.ObjectStatus.DELAYED;
import static br.com.bomrastreio.api.object.ObjectStatus.LOSS;
import static java.time.Instant.now;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DashboardServiceTest {

  public static final String CURRENT_TENANT = "123456";
  public static final String CODE_1 = "RS510516733CN";
  public static final String CODE_2 = "RS510516733CM";

  @Autowired
  private DashboardService dashboardService;
  private DashboardService dashboardServiceSpy;
  @Autowired
  private ObjectService objectService;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException {
    dashboardServiceSpy = spy(dashboardService);
    doReturn(CURRENT_TENANT).when(dashboardServiceSpy).getUserName();

    createTrackingObject(CODE_1, LOSS);
    createTrackingObject(CODE_2, DELAYED);
  }

  @Test
  public void summarize() {
    DefaultDashboard summarize = dashboardServiceSpy.summarize();

    assertEquals(2L, summarize.getTotalCount());
    assertEquals(1L, summarize.getDelayedCount());
    assertEquals(1L, summarize.getLossCount());
  }

  private void createTrackingObject(String code, ObjectStatus objectStatus) throws NoSuchFieldException, IllegalAccessException {
    TrackingObject object = TrackingObject.builder()
      .withId(UUID.randomUUID().toString())
      .withCode(code)
      .withEta(now())
      .withAddressee("Destinatário")
      .build();
    setPrivateField(object,"status", objectStatus);

    TenantHolder.set(CURRENT_TENANT);
    objectService.save(object);
  }

  private void setPrivateField(TrackingObject resource, String field, ObjectStatus value) throws NoSuchFieldException, IllegalAccessException {
    Field f = resource.getClass().getDeclaredField(field);
    f.setAccessible(true);
    f.set(resource, value);
  }

}
