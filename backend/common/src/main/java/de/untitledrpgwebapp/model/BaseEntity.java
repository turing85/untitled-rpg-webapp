package de.untitledrpgwebapp.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class BaseEntity<T extends BaseEntity<T>> {

  private final Long id;

  public final Long getId() {
    return id;
  }

  protected abstract T self();
}
