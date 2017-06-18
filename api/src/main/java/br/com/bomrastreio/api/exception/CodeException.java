package br.com.bomrastreio.api.exception;

import org.springframework.http.HttpStatus;

public abstract class CodeException extends RuntimeException {

  private final String code;
  private final Object[] arguments;

  CodeException(String code) {
    this(code, new Object[]{});
  }

  CodeException(String code, Object... arguments) {
    this.code = code;
    this.arguments = arguments;
  }

  public abstract HttpStatus getHttpStatus();

  public String getCode() {
    return code;
  }

  public Object[] getArguments() {
    return arguments;
  }
}
