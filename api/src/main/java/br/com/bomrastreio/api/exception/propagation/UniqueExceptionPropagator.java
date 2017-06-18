package br.com.bomrastreio.api.exception.propagation;

import br.com.bomrastreio.api.exception.UniqueObjectException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Component
public class UniqueExceptionPropagator implements ExceptionPropagable {

  @Override
  public Optional<RuntimeException> tryPropagate(PropagationContext context) {
    if (canPropagate(context)) {
      return Optional.of(propagate(context));
    }

    return Optional.empty();
  }

  private boolean canPropagate(PropagationContext context) {
    Preconditions.checkNotNull(context, "Context cannot be null");
    return SQLException.class.isInstance(context.getThrowable())
      && ((SQLException) context.getThrowable()).getErrorCode() == 1062;
  }

  private RuntimeException propagate(PropagationContext context) {
    Object[] arguments = context.getArgumentsOr(extractArguments(context));
    return new UniqueObjectException(context.getCode("unique"), arguments);
  }

  /**
   * Try to extract the text between ' e-mail, code ' that represents the
   * keys involved in the unique representation.
   *
   * @param context Exception
   * @return Information from the exception message
   */
  private Object[] extractArguments(PropagationContext context) {
    Throwable throwable = context.getThrowable();
    String values = StringUtils.substringBetween(throwable.getMessage(), "'", "'");

    if (Objects.nonNull(values)) {
      return values.split("-");
    }

    return new Object[]{};
  }
}
