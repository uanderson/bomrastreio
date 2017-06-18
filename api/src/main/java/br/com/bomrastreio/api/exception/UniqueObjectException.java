package br.com.bomrastreio.api.exception;

import org.springframework.http.HttpStatus;

public class UniqueObjectException extends CodeException {

  public UniqueObjectException(String code, Object... arguments) {
    super(code, arguments);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }

}
