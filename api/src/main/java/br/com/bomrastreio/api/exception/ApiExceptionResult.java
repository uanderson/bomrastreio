package br.com.bomrastreio.api.exception;

import static com.google.common.base.Preconditions.checkNotNull;

public class ApiExceptionResult {

  private final String code;
  private final String message;

  ApiExceptionResult(String code, String message) {
    this.code = checkNotNull(code);
    this.message = checkNotNull(message);
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
