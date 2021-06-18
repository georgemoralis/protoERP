/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 17/11/2020 (georgemoralis) - Added name and active variables
 * 30/10/2020 (georgemoralis) - Implements displayable
 * 24/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.lib.crud.intf.Displayable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_users")
public class depr_UsersEntity implements Serializable, Displayable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String username;
  @Getter @Setter private String password;
  @Getter @Setter private Boolean active;
  @Getter @Setter private String name;

  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "protoerp_user_role",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private List<depr_RolesEntity> roleList = new ArrayList<>();

  @Transient private Set<String> rolesName;

  public Set<String> getRolesName() {
    List<depr_RolesEntity> roles = getRoleList();
    rolesName = new HashSet<>();
    for (depr_RolesEntity role : roles) {
      rolesName.add(role.getRoleName());
    }
    return rolesName;
  }

  public List<depr_RolesEntity> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<depr_RolesEntity> roleList) {
    this.roleList = roleList;
  }

  @Override
  public String getComboDisplayValue() {
    return username;
  }
}
