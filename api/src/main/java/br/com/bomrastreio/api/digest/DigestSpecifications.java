package br.com.bomrastreio.api.digest;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class DigestSpecifications {

  public static Specification<Digest> isCollectable(Digest digest) {
    return (root, query, builder) -> builder.and(
      builder.equal(root.get(Digest_.type), digest.getType()),
      builder.equal(root.get(Digest_.reference), digest.getReference()),
      builder.equal(root.get(Digest_.date), digest.getDate())
    );
  }

  public static Specification<Digest> isDeliverableNow() {
    return (root, query, builder) -> builder.equal(root.get(Digest_.date), LocalDate.now());
  }
}
