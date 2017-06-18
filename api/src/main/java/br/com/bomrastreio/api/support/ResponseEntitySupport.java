package br.com.bomrastreio.api.support;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

/**
 * Provides custom {@link ResponseEntity} definition for the RESTful API.
 */
public final class ResponseEntitySupport {

  /**
   * Respond as created (201) and also put the resource
   * link in the response.
   *
   * @param id   New resource id
   * @param body New resource body
   * @return Customized response entity
   */
  public static <T> ResponseEntity<T> created(String id, T body) {
    UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest()
      .pathSegment(id)
      .build();

    return ResponseEntity.created(uriComponents.toUri()).body(body);
  }

  /**
   * Transforms a page into a list, but put the total count as
   * header for client pagination.
   *
   * @param page Page content
   * @return List with resources
   */
  public static <T> ResponseEntity<List<T>> paged(Page<T> page) {
    return ResponseEntity.ok()
      .header(CustomHeaders.TOTAL_COUNT, String.valueOf(page.getNumberOfElements()))
      .body(page.getContent());
  }

  private ResponseEntitySupport() {
  }
}
