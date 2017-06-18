package br.com.bomrastreio.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ApiMultipleExceptionResult extends ApiExceptionResult {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final List<IndexedError> errors = new ArrayList<>();

  public ApiMultipleExceptionResult(String code, String message) {
    super(code, message);
  }

  public void addError(IndexedError indexedError) {
    this.errors.add(checkNotNull(indexedError));
  }

  public List<IndexedError> getErrors() {
    return ImmutableList.copyOf(errors);
  }
}
