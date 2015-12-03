/*
 * Copyright (c) 2015 Franjo Žilić <frenky666@gmail.com>
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.fzilic.spring.rate.limiting.options.annotation;

import com.github.fzilic.spring.rate.limiting.options.Options;
import com.github.fzilic.spring.rate.limiting.options.OptionsInterval;
import com.github.fzilic.spring.rate.limiting.options.OptionsRetry;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AnnotationOptions implements Options {

  protected static class InternalInterval implements OptionsInterval {

    private Long interval;

    private TimeUnit unit;

    @Override
    public int hashCode() {
      return new HashCodeBuilder()
          .append(interval)
          .append(unit)
          .toHashCode();
    }

    @Override
    public Long interval() {
      return interval;
    }

    @Override
    public TimeUnit unit() {
      return unit;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof InternalInterval)) {
        return false;
      }
      InternalInterval rhs = (InternalInterval) obj;
      return new EqualsBuilder()
          .append(this.interval, rhs.interval)
          .append(this.unit, rhs.unit)
          .isEquals();
    }


  }

  protected static class InternalRetry implements OptionsRetry {

    private Integer retryCount;

    private OptionsInterval interval;

    @Override
    public Integer retryCount() {
      return retryCount;
    }

    @Override
    public OptionsInterval retryInterval() {
      return interval;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof InternalRetry)) {
        return false;
      }
      InternalRetry rhs = (InternalRetry) obj;
      return new EqualsBuilder()
          .append(this.retryCount, rhs.retryCount)
          .append(this.interval, rhs.interval)
          .isEquals();
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder()
          .append(retryCount)
          .append(interval)
          .toHashCode();
    }

  }

  private String resolvedKey;

  private boolean retryEnabled;

  private boolean enabled;

  private OptionsInterval interval;

  private Long maxRequests;

  private OptionsRetry retry;

  public static AnnotationOptions disabled(final String resolvedKey) {
    final AnnotationOptions options = new AnnotationOptions();
    options.enabled = false;
    options.resolvedKey = resolvedKey;
    return options;
  }

  public static AnnotationOptions enabled(final String resolvedKey, final long maxRequests, final OptionsInterval interval) {
    final AnnotationOptions options = new AnnotationOptions();
    options.enabled = true;
    options.resolvedKey = resolvedKey;
    options.maxRequests = maxRequests;
    options.interval = interval;
    return options;
  }

  public static InternalInterval intervalOf(final Long interval, final TimeUnit unit) {
    final InternalInterval internal = new InternalInterval();
    internal.interval = interval;
    internal.unit = unit;
    return internal;
  }

  @Override
  public boolean blocked() {
    // not applicable for annotation configuration
    return false;
  }

  @Override
  public boolean enabled() {
    return enabled;
  }

  @Override
  public OptionsInterval interval() {
    return interval;
  }

  @Override
  public Long maxRequests() {
    return maxRequests;
  }

  @Override
  public String resolvedKey() {
    return resolvedKey;
  }

  @Override
  public OptionsRetry retry() {
    return retry;
  }

  @Override
  public boolean retryEnabled() {
    return retryEnabled;
  }

  public AnnotationOptions enableRetry(final Integer retryCount, OptionsInterval interval) {
    this.retryEnabled = true;
    this.retry = retryOf(retryCount, interval);
    return this;
  }

  public static InternalRetry retryOf(final Integer retryCount, final OptionsInterval interval) {
    final InternalRetry internalRetry = new InternalRetry();
    internalRetry.retryCount = retryCount;
    internalRetry.interval = interval;
    return internalRetry;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AnnotationOptions)) {
      return false;
    }
    AnnotationOptions rhs = (AnnotationOptions) obj;
    return new EqualsBuilder()
        .append(this.resolvedKey, rhs.resolvedKey)
        .append(this.retryEnabled, rhs.retryEnabled)
        .append(this.enabled, rhs.enabled)
        .append(this.interval, rhs.interval)
        .append(this.maxRequests, rhs.maxRequests)
        .append(this.retry, rhs.retry)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(resolvedKey)
        .append(retryEnabled)
        .append(enabled)
        .append(interval)
        .append(maxRequests)
        .append(retry)
        .toHashCode();
  }

}
