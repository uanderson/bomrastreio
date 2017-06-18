package br.com.bomrastreio.api.digest;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.bomrastreio.api.digest.DigestSpecifications.isCollectable;
import static br.com.bomrastreio.api.digest.DigestSpecifications.isDeliverableNow;

@Service
public class DigestService {

  private final DigestRepository digestRepository;
  private final DigestReferenceFinder digestReferenceFinder;

  @Autowired
  public DigestService(
    DigestRepository digestRepository, DigestReferenceFinder digestReferenceFinder
  ) {
    this.digestRepository = digestRepository;
    this.digestReferenceFinder = digestReferenceFinder;
  }

  /**
   * Collect the digest if wasn't collected before.
   *
   * @param digest Digest to be collected
   */
  @Transactional
  public void collect(Digest digest) {
    Preconditions.checkNotNull(digest, "Digest cannot be null");
    long count = digestRepository.count(isCollectable(digest));

    if (count == 0L) {
      digestRepository.save(digest);
    }
  }

  @Transactional(readOnly = true)
  public List<Digest> findDeliverables() {
    return digestRepository.findAll(isDeliverableNow());
  }

  @Transactional(readOnly = true)
  public Set<String> findDeliverableTenants() {
    return digestRepository.findDeliverableTenants();
  }

  @Transactional(readOnly = true)
  public List<?> findReferences(DigestType type, List<Digest> digests) {
    return digestReferenceFinder
      .findReferences(type, digests.stream()
        .map(Digest::getReference)
        .collect(Collectors.toSet()));
  }

}
