package br.com.bomrastreio.api.object;

import org.springframework.data.jpa.domain.Specification;

public final class ObjectSpecifications {

  public static Specification<TrackingObject> isTraceable() {
    return (root, query, builder) -> builder
      .notEqual(root.get(TrackingObject_.status), ObjectStatus.DELIVERED);
  }

}
