package br.com.bomrastreio.api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CodeException {

  public NotFoundException(String code, Object... arguments) {
    super(code, arguments);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }

}
