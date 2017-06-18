package br.com.bomrastreio.api.exception;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class IndexedError {

  private final int index;
  private final List<FieldError> fieldErrors;

  public IndexedError(int index, List<FieldError> fieldErrors) {
    this.index = index;
    this.fieldErrors = fieldErrors;
  }

  public void addError(FieldError fieldError) {
    this.fieldErrors.add(checkNotNull(fieldError));
  }

  public int getIndex() {
    return this.index;
  }

  public List<FieldError> getFieldErrors() {
    return ImmutableList.copyOf(fieldErrors);
  }
}
