package br.com.bomrastreio.api.object.event;

import br.com.bomrastreio.api.correios.trace.wsdl.TraceEvent;
import br.com.bomrastreio.api.object.TrackingObject;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.ApplicationEvent;

public class ObjectStatusChangedEvent extends ApplicationEvent {

  private final TraceEvent traceEvent;
  private final String tenant;

  private ObjectStatusChangedEvent(Builder builder) {
    super(builder.object);
    this.traceEvent = builder.traceEvent;
    this.tenant = builder.tenant;
  }

  public TrackingObject getObject() {
    return (TrackingObject) getSource();
  }

  public TraceEvent getTraceEvent() {
    return this.traceEvent;
  }

  public String getTenant() {
    return this.tenant;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private TrackingObject object;
    private TraceEvent traceEvent;
    private String tenant;

    public Builder withObject(TrackingObject object) {
      this.object = object;
      return this;
    }

    public Builder withTraceEvent(TraceEvent traceEvent) {
      this.traceEvent = traceEvent;
      return this;
    }

    public Builder withTenant(String tenant) {
      this.tenant = tenant;
      return this;
    }

    public ObjectStatusChangedEvent build() {
      Preconditions.checkNotNull(object, "Object cannot be null");
      Preconditions.checkNotNull(traceEvent, "Trace event cannot be null");
      Preconditions.checkNotNull(tenant, "Tenant cannot be null");

      return new ObjectStatusChangedEvent(this);
    }
  }
}
