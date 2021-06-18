/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 16/11/2020 (georgemoralis) - Changed role_permission table to ManyToMany for permissions
 * 24/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.userManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Table(name = "protoerp_roles")
public class depr_RolesEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String roleName;

  @ManyToMany
  @JoinTable(
      name = "protoerp_role_permission",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
  private List<depr_PermissionsEntity> permissionList = new ArrayList<>();

  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "roleList")
  private List<depr_UsersEntity> userList = new ArrayList<>();

  @Transient private Set<String> permissionsName;

  public Set<String> getPermissionsName() {
    permissionsName = new HashSet<>();
    List<depr_PermissionsEntity> perlist = getPermissionList();
    for (depr_PermissionsEntity per : perlist) {
      permissionsName.add(per.getPermissionName());
    }
    return permissionsName;
  }

  public List<depr_PermissionsEntity> getPermissionList() {
    return permissionList;
  }

  public void setPermissionList(List<depr_PermissionsEntity> permissionList) {
    this.permissionList = permissionList;
  }

  public List<depr_UsersEntity> getUserList() {
    return userList;
  }

  public void setUserList(List<depr_UsersEntity> userList) {
    this.userList = userList;
  }
}
