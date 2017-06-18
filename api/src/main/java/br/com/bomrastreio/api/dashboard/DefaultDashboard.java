package br.com.bomrastreio.api.dashboard;

public class DefaultDashboard {

  private long totalCount;
  private long deliveredCount;
  private long delayedCount;
  private long lossCount;

  private DefaultDashboard(Builder builder) {
    this.totalCount = builder.totalCount;
    this.deliveredCount = builder.deliveredCount;
    this.delayedCount = builder.delayedCount;
    this.lossCount = builder.lossCount;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public long getDeliveredCount() {
    return deliveredCount;
  }

  public long getDeliveredPercentage() {
    return calculatePercentage(deliveredCount);
  }

  public long getDelayedCount() {
    return delayedCount;
  }

  public long getDelayedPercentage() {
    return calculatePercentage(delayedCount);
  }

  public long getLossCount() {
    return lossCount;
  }

  public long getLossPercentage() {
    return calculatePercentage(lossCount);
  }

  private long calculatePercentage(long count) {
    if (totalCount == 0) {
      return 0;
    }

    return (count * 100) / totalCount;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private long totalCount;
    private long deliveredCount;
    private long delayedCount;
    private long lossCount;

    public Builder withTotalCount(long totalCount) {
      this.totalCount = totalCount;
      return this;
    }

    public Builder withDeliveredCount(long deliveredCount) {
      this.deliveredCount = deliveredCount;
      return this;
    }

    public Builder withDelayedCount(long delayedCount) {
      this.delayedCount = delayedCount;
      return this;
    }

    public Builder withLossCount(long lossCount) {
      this.lossCount = lossCount;
      return this;
    }

    public DefaultDashboard build() {
      return new DefaultDashboard(this);
    }

  }
}
