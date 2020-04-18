package de.untitledrpgwebapp.user.impl.quarkus.boundary.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@RegisterForReflection
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDto extends CommonUserDto {
}