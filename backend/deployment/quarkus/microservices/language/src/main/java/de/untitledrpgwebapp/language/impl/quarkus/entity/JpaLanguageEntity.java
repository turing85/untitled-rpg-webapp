package de.untitledrpgwebapp.language.impl.quarkus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class JpaLanguageEntity {

  @Id
  @Column(name = "tag", nullable = false, unique = true)
  private String tag;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  public String getTag() {
    return tag;
  }

  public JpaLanguageEntity setTag(String tag) {
    this.tag = tag;
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
