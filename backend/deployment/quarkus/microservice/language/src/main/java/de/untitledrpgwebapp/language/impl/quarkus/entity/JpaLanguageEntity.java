package de.untitledrpgwebapp.language.impl.quarkus.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JpaLanguageEntity {
  @Id
  private String code;

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
