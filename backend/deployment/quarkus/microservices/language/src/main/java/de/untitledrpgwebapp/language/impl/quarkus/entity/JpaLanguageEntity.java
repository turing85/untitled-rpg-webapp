package de.untitledrpgwebapp.language.impl.quarkus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "language")
public class JpaLanguageEntity {

  @Id
  @Column(name = "tag", nullable = false, unique = true)
  private String tag;

  @Column(name = "name", nullable = false, unique = true)
  private String name;
}
