package br.com.bomrastreio.api.digest;

import br.com.bomrastreio.api.object.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class DigestReferenceFinder {

  private ObjectService objectService;

  @Autowired
  public DigestReferenceFinder(ObjectService objectService) {
    this.objectService = objectService;
  }

  @Transactional(readOnly = true)
  public List<?> findReferences(DigestType type, Set<String> references) {
    if (DigestType.OBJECT.equals(type)) {
      return objectService.findByCodes(references);
    }

    throw new IllegalArgumentException("Not implemented yet for type " + type);
  }

}
