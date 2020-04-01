package de.untitledrpgwebapp.boundary;

import java.util.UUID;

public interface Transfer<T extends Transfer<T>> {

  UUID getCorrelationId();

  T setCorrelationId(UUID correlationId);
}
