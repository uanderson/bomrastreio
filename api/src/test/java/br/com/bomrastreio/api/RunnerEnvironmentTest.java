package br.com.bomrastreio.api;

import br.com.bomrastreio.api.dashboard.DashboardService;
import br.com.bomrastreio.api.object.ObjectService;
import br.com.bomrastreio.api.object.ObjectStatus;
import br.com.bomrastreio.api.object.TrackingObject;
import br.com.bomrastreio.api.tenant.TenantHolder;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

import static java.time.Instant.now;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RunnerEnvironmentTest {

  public static final String CURRENT_TENANT = "porcelani@gmail.com";

  @Autowired
  private DashboardService dashboardService;
  @Autowired
  private ObjectService objectService;

  private DashboardService dashboardServiceSpy;


  @Test
  public void environment_runner() {
    create_20_tracking_objects_forech_object_status();
  }

  @After
  public void frizzing_server() {
    Scanner in = new Scanner(System.in);
    in.nextInt();
  }

  private void create_20_tracking_objects_forech_object_status() {
    dashboardServiceSpy = spy(dashboardService);
    doReturn(CURRENT_TENANT).when(dashboardServiceSpy).getUserName();

    for (int i = 1; i <= 20; i++) {
      Arrays.asList(ObjectStatus.values()).forEach(objectStatus -> {
        createTrackingObject(String.valueOf(Math.random()).substring(0, 12), objectStatus);
      });
    }
  }


  private void createTrackingObject(String code, ObjectStatus objectStatus) {
    TrackingObject object = TrackingObject.builder()
      .withId(UUID.randomUUID().toString())
      .withCode(code)
      .withEta(now())
      .withAddressee("Destinatário")
      .build();
    try {
      setPrivateField(object, "status", objectStatus);
    } catch (Exception e) {
      throw new RuntimeException("field status error");
    }

    TenantHolder.set(CURRENT_TENANT);
    objectService.save(object);
  }

  private void setPrivateField(TrackingObject resource, String field, ObjectStatus value) throws NoSuchFieldException, IllegalAccessException {
    Field f = resource.getClass().getDeclaredField(field);
    f.setAccessible(true);
    f.set(resource, value);
  }

}
