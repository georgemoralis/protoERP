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
@Table(name = "roles")
public class RolesEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String roleName;

  @ManyToMany
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
  private List<PermissionsEntity> permissionList = new ArrayList<>();

  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "roleList")
  private List<UserEntity> userList = new ArrayList<>();

  @Transient private Set<String> permissionsName;

  public Set<String> getPermissionsName() {
    permissionsName = new HashSet<>();
    List<PermissionsEntity> perlist = getPermissionList();
    for (PermissionsEntity per : perlist) {
      permissionsName.add(per.getPermissionName());
    }
    return permissionsName;
  }

  public List<PermissionsEntity> getPermissionList() {
    return permissionList;
  }

  public void setPermissionList(List<PermissionsEntity> permissionList) {
    this.permissionList = permissionList;
  }

  public List<UserEntity> getUserList() {
    return userList;
  }

  public void setUserList(List<UserEntity> userList) {
    this.userList = userList;
  }
}
