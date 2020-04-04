package de.untitledrpgwebapp.language.impl.quarkus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class JpaLanguageEntity {

  @Id
  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  public String getCode() {
    return code;
  }

  public JpaLanguageEntity setCode(String code) {
    this.code = code;
    return this;
  }

  public String getName() {
    return name;
  }

  public JpaLanguageEntity setName(String name) {
    this.name = name;
    return this;
  }
}
