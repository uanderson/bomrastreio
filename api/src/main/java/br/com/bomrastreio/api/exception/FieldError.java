package br.com.bomrastreio.api.exception;

import static com.google.common.base.Preconditions.checkNotNull;

public class FieldError {

  private final String field;
  private final String message;

  public FieldError(String field, String message) {
    this.field = checkNotNull(field);
    this.message = checkNotNull(message);
  }

  public String getField() {
    return this.field;
  }

  public String getMessage() {
    return this.message;
  }

}
