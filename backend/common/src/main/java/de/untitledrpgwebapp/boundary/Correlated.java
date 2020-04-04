package de.untitledrpgwebapp.boundary;

import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class Correlated<C extends Correlated<C>> {

  final UUID correlationId;
}
