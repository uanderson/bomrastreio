package br.com.bomrastreio.api.digest;

import br.com.bomrastreio.api.tenant.TenantHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DigestServiceTest {

  @Autowired
  private DigestService digestService;

  @Test
  public void should_save_when_not_exists() {
    TenantHolder.set("this@uandersonsoar.es");

    List<Digest> deliverablesBefore = digestService.findDeliverables();
    digestService.collect(createDigest("PR123456789BR"));

    List<Digest> deliverablesAfter = digestService.findDeliverables();

    assertThat(deliverablesAfter, notNullValue());
    assertThat(deliverablesAfter, hasSize(deliverablesBefore.size() + 1));
  }

  @Test
  public void should_not_save_when_exists() {
    TenantHolder.set("this@uandersonsoar.es");
    digestService.collect(createDigest("PR987654321BR"));

    List<Digest> deliverablesBefore = digestService.findDeliverables();
    digestService.collect(createDigest("PR987654321BR"));

    List<Digest> deliverablesAfter = digestService.findDeliverables();

    assertThat(deliverablesAfter, notNullValue());
    assertThat(deliverablesAfter, hasSize(deliverablesBefore.size()));
  }

  private Digest createDigest(String reference) {
    return Digest.builder()
      .withRandomId()
      .withReference(reference)
      .withType(DigestType.OBJECT)
      .build();
  }

}
