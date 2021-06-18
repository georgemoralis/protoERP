/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 25/10/2020 (georgemoralis) - added testPermissions
 */
package gr.codebb.protoerp.tests;

import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.userManagement.depr_PermissionsEntity;
import gr.codebb.protoerp.userManagement.depr_RolesEntity;
import gr.codebb.protoerp.userManagement.depr_UsersEntity;
import org.apache.shiro.authc.credential.DefaultPasswordService;

public class UserManagementTests {
  public void createPermissions() {
    // define permissions
    final depr_PermissionsEntity p1 = new depr_PermissionsEntity();
    p1.setPermissionName("VIEW_ALL_USERS");
    GenericDao gdaoi1 = new GenericDao(depr_PermissionsEntity.class, PersistenceManager.getEmf());
    gdaoi1.createEntity(p1);
    final depr_PermissionsEntity p2 = new depr_PermissionsEntity();
    p2.setPermissionName("USER_MANAGEMENT");
    p2.setPermissionDisplayName("Διαχείριση Χρηστών");
    gdaoi1.createEntity(p2);
    // define roles
    final depr_RolesEntity roleAdmin = new depr_RolesEntity();
    roleAdmin.setRoleName("ADMIN");
    roleAdmin.getPermissionList().add(p1);
    roleAdmin.getPermissionList().add(p2);
    System.out.println("roleAdmin.getId() 1: " + roleAdmin.getId());
    GenericDao gdaoi2 = new GenericDao(depr_RolesEntity.class, PersistenceManager.getEmf());
    gdaoi2.createEntity(roleAdmin);
    System.out.println("roleAdmin.getId() 2: " + roleAdmin.getId());
    // define user
    DefaultPasswordService passwordService = new DefaultPasswordService();
    final depr_UsersEntity user = new depr_UsersEntity();
    user.setUsername("admin");
    user.setPassword(passwordService.encryptPassword("admin"));
    user.getRoleList().add(roleAdmin);
    GenericDao gdaoi3 = new GenericDao(depr_UsersEntity.class, PersistenceManager.getEmf());
    gdaoi3.createEntity(user);
  }

  public void testPermissions() {
    /*    UsersEntity user = UserQueries.findUserByUsername("admin");
    System.out.println("roles " + user.getRoleList().toString());
    for (RolesEntity role : user.getRoleList()) {
      for (Iterator iterator = role.getPermissionList().iterator(); iterator.hasNext(); ) {
        PermissionsEntity permission = (PermissionsEntity) iterator.next();
        System.out.println(permission.getPermissionName());
      }
    }*/
  }
}
