package br.com.bomrastreio.api.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CodeException {

  public BadRequestException(String code, Object... arguments) {
    super(code, arguments);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }

}
