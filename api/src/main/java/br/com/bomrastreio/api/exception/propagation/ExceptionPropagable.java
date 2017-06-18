package br.com.bomrastreio.api.exception.propagation;

import java.util.Optional;

/**
 * Enable a class to handle exception propagation.
 */
public interface ExceptionPropagable {

  /**
   * Check if the context passed could be propagated. This method
   * returns a optional of {@link RuntimeException}, and allows the
   * client to check if is possible to thrown it.
   *
   * @param context Context with exception to be checked
   * @return Present RuntimeException if can propagate
   *         Absent RuntimeException otherwise
   */
  Optional<RuntimeException> tryPropagate(PropagationContext context);

}
