package br.com.bomrastreio.api.exception.propagation;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

/**
 * It keeps data for propagation processing.
 */
public class PropagationContext {

  private Throwable throwable;
  private String code = "";
  private String codePrefix = "";
  private Object[] arguments = new Object[]{};
  private ExceptionPropagator exceptionPropagator;

  protected PropagationContext(ExceptionPropagator exceptionPropagator) {
    this.exceptionPropagator = exceptionPropagator;
  }

  public String getSource() {
    return codePrefix;
  }

  public PropagationContext withCodePrefix(String codePrefix) {
    this.codePrefix = codePrefix;
    return this;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public PropagationContext withThrowable(Throwable throwable) {
    this.throwable = throwable;
    return this;
  }

  public String getCode() {
    return code;
  }

  /**
   * Create a code based on the code prefix. The join will generate
   * a code like codePrefix.complement. If this method was called and
   * no code prefix was informed, the result will be the normal code.
   *
   * @param complement Complement to the prefix code
   * @return Prefixed code if codePrefix was informed, code otherwise
   */
  public String getCode(String complement) {
    Preconditions.checkNotNull(complement, "Complement cannot be null");

    if (StringUtils.isNotBlank(codePrefix)) {
      return codePrefix + "." + complement;
    }

    return code;
  }

  public PropagationContext withCode(String code) {
    this.code = code;
    return this;
  }

  public Object[] getArguments() {
    return arguments;
  }

  /**
   * Try to now the arguments, but if wasn't informed, return
   * the alternative passed as parameter.
   *
   * @param arguments Arguments to be returned in case of absence
   * @return These arguments if the size is zero, the alternative otherwise
   */
  public Object[] getArgumentsOr(Object... arguments) {
    Preconditions.checkNotNull(arguments, "Arguments cannot be null");

    if (this.arguments.length > 0) {
      return this.arguments;
    }

    return arguments;
  }

  public PropagationContext withArguments(Object... arguments) {
    this.arguments = arguments;
    return this;
  }

  /**
   * It delegate the propagation call to the {@link ExceptionPropagator}
   * passing itself as parameter.
   *
   * @return A new runtime exception
   */
  public RuntimeException now() {
    Preconditions.checkNotNull(throwable, "Throwable cannot be null");
    Preconditions.checkNotNull(code, "Code cannot be null");
    Preconditions.checkNotNull(codePrefix, "Code prefix cannot be null");
    Preconditions.checkNotNull(arguments, "Arguments cannot be null");

    if (StringUtils.isBlank(code) && StringUtils.isBlank(codePrefix)) {
      throw new IllegalStateException("Code or code prefix must be informed");
    }

    return exceptionPropagator.propagate(this);
  }

}
