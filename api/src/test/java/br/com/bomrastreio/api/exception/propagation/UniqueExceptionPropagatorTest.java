package br.com.bomrastreio.api.exception.propagation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UniqueExceptionPropagatorTest {

  @InjectMocks
  private UniqueExceptionPropagator uniqueExceptionPropagator;

  @Test
  public void should_propagate_with_arguments() {
    PropagationContext propagationContext = createPropagationContext(createSQLException(1062));
    Optional<RuntimeException> exception = uniqueExceptionPropagator.tryPropagate(propagationContext);
    assertThat(exception, is(notNullValue()));
    assertThat(exception.isPresent(), is(true));
  }

  @Test
  public void should_not_propagate_when_not_unique_code() {
    PropagationContext propagationContext = createPropagationContext(createSQLException(2030));
    Optional<RuntimeException> runtimeException = uniqueExceptionPropagator.tryPropagate(propagationContext);
    assertThat(runtimeException, is(notNullValue()));
    assertThat(runtimeException.isPresent(), is(false));
  }

  @Test
  public void should_not_propagate_when_not_sql_exception() {
    PropagationContext propagationContext = createPropagationContext(new RuntimeException());
    Optional<RuntimeException> runtimeException = uniqueExceptionPropagator.tryPropagate(propagationContext);
    assertThat(runtimeException, is(notNullValue()));
    assertThat(runtimeException.isPresent(), is(false));
  }

  private SQLException createSQLException(int code) {
    SQLException exception = mock(SQLException.class);
    when(exception.getErrorCode()).thenReturn(code);
    when(exception.getMessage()).thenReturn("Duplicated key 'app@testing.com-PR000000000BR' for indice idx_");

    return exception;
  }

  private PropagationContext createPropagationContext(Throwable throwable) {
    PropagationContext propagationContext = mock(PropagationContext.class);
    when(propagationContext.getThrowable()).thenReturn(throwable);
    when(propagationContext.getCode()).thenReturn("entity.duplicated");

    return propagationContext;
  }
}
