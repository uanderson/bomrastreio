package br.com.bomrastreio.api.exception;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path.Node;
import javax.validation.Path.PropertyNode;
import java.util.List;
import java.util.Objects;

/**
 * Custom API exception handler.
 */
@ControllerAdvice
public class ApiExceptionHandler {

  public static final String GENERIC_UNPROCESSABLE_ENTITY_CODE = "generic.unprocessable_entity";

  private MessageSource messageSource;

  @Autowired
  public ApiExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Handle {@link CodeException} exceptions.
   *
   * @param ex Exception to be handled
   * @return Response entity with the error
   */
  @ExceptionHandler(CodeException.class)
  public ResponseEntity<ApiExceptionResult> codeException(CodeException ex) {
    String localizedMessage = findLocalizedMessage(ex.getCode(), ex.getArguments());
    ApiExceptionResult exceptionResult = new ApiExceptionResult(ex.getCode(), localizedMessage);

    return ResponseEntity
      .status(ex.getHttpStatus())
      .body(exceptionResult);
  }

  /**
   * Handle {@link ConstraintViolationException} exceptions.
   *
   * @param ex Exception to be handled
   * @return Response entity with the error
   */
  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex) {
    Multimap<Integer, FieldError> mapper = ArrayListMultimap.create();
    ex.getConstraintViolations().forEach(violation -> collectFieldErrors(mapper, violation));

    String localizedMessage = findLocalizedMessage(GENERIC_UNPROCESSABLE_ENTITY_CODE);
    ApiMultipleExceptionResult exceptionResult = new ApiMultipleExceptionResult(GENERIC_UNPROCESSABLE_ENTITY_CODE, localizedMessage);

    mapper.asMap().forEach((key, value) ->
      exceptionResult.addError(new IndexedError(key, (List<FieldError>) value)));

    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
      .body(exceptionResult);
  }

  /**
   * Collect field errors to a {@link Multimap}. This allows the errors to be collected
   * by index, grouping them to send back to user.
   *
   * @param mapper    Multimap collector
   * @param violation Current violation
   */
  private void collectFieldErrors(Multimap<Integer, FieldError> mapper, ConstraintViolation<?> violation) {
    for (Node node : violation.getPropertyPath()) {
      if (ElementKind.PROPERTY.equals(node.getKind())) {
        PropertyNode propertyNode = node.as(PropertyNode.class);
        FieldError fieldError = new FieldError(propertyNode.getName(), violation.getMessage());

        if (Objects.nonNull(node.getIndex())) {
          mapper.put(propertyNode.getIndex(), fieldError);
        } else {
          mapper.put(0, fieldError);
        }
      }
    }
  }

  /**
   * Find localized message based on the code.
   *
   * @param code I18N message code
   * @return Localized message
   */
  private String findLocalizedMessage(String code) {
    return findLocalizedMessage(code, new Object[]{});
  }

  /**
   * Find localized message based on the code. Also, apply the arguments
   * to the message.
   *
   * @param code      I18N message code
   * @param arguments Arguments for the message
   * @return Localized message
   */
  private String findLocalizedMessage(String code, Object... arguments) {
    return messageSource.getMessage(code, arguments, LocaleContextHolder.getLocale());
  }
}
