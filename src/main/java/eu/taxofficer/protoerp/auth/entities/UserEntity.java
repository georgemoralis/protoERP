/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.auth.entities;

import gr.codebb.lib.crud.intf.Displayable;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** @author George Moralis */
@Entity
@Table(name = "protoerp_users")
public class UserEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String username;
  @Getter @Setter private String password;
  @Getter @Setter private Boolean active;
  @Getter @Setter private String name;

  @ManyToMany
  @JoinTable(
      name = "protoerp_user_role",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  @Getter
  @Setter
  private Set<RoleEntity> roles;

  @Override
  public String getComboDisplayValue() {
    return username;
  }
}
