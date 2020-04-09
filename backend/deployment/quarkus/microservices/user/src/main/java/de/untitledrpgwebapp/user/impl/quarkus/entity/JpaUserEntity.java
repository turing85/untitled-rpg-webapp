package de.untitledrpgwebapp.user.impl.quarkus.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class JpaUserEntity {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "preferred_language_tag", nullable = false)
  private String preferredLanguageTag = "en_US";

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "bio")
  private String bio;

  @Column(name = "avatar")
  private byte[] avatar;
}
