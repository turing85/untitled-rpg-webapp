package de.untitledrpgwebapp.language.boundary.spi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "language")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class JpaLanguageEntity {

  @Id
  @Column(name = "tag", nullable = false, unique = true)
  private String tag;

  @Column(name = "name", nullable = false, unique = true)
  private String name;
}
