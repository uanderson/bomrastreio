package br.com.bomrastreio.api.exception.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Delegate propagation to exception propagators registered in the
 * application through the {@link ExceptionPropagable} interface.
 */
@Component
public class ExceptionPropagator {

  private final List<ExceptionPropagable> propagators;

  @Autowired
  private ExceptionPropagator(List<ExceptionPropagable> propagators) {
    this.propagators = propagators;
  }

  /**
   * Start a context for propagation.
   *
   * @return A new {@link PropagationContext}
   */
  public PropagationContext propagate() {
    return new PropagationContext(this);
  }

  /**
   * It iterates over the propagators to see which one
   * is capable of handle the context with the throwable. If found,
   * interrupt the searching and return the exception.
   *
   * @param context Current exception context
   * @return Runtime exception converted by the propagator,
   *         Default exception if no one was capable of handling it
   */
  protected RuntimeException propagate(PropagationContext context) {
    for (ExceptionPropagable propagator : propagators) {
      Optional<RuntimeException> optionalException = propagator.tryPropagate(context);

      if (optionalException.isPresent()) {
        return optionalException.get();
      }
    }

    return new RuntimeException(context.getThrowable());
  }
}
