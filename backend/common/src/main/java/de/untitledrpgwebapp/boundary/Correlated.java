package de.untitledrpgwebapp.boundary;

import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

public interface Correlated {

  UUID getCorrelationId();
}
