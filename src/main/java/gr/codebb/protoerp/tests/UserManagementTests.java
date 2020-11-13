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
import gr.codebb.protoerp.userManagement.PermissionEntity;
import gr.codebb.protoerp.userManagement.RoleEntity;
import gr.codebb.protoerp.userManagement.UserEntity;
import gr.codebb.protoerp.userManagement.UserQueries;
import java.util.Iterator;
import org.apache.shiro.authc.credential.DefaultPasswordService;

public class UserManagementTests {
  public void createPermissions() {
    // define permissions
    final PermissionEntity p1 = new PermissionEntity();
    p1.setPermissionName("VIEW_ALL_USERS");
    GenericDao gdaoi1 = new GenericDao(PermissionEntity.class, PersistenceManager.getEmf());
    gdaoi1.createEntity(p1);
    final PermissionEntity p2 = new PermissionEntity();
    p2.setPermissionName("USER_MANAGEMENT");
    p2.setPermissionDisplayName("Διαχείριση Χρηστών");
    gdaoi1.createEntity(p2);
    // define roles
    final RoleEntity roleAdmin = new RoleEntity();
    roleAdmin.setRoleName("ADMIN");
    roleAdmin.getPermissionList().add(p1);
    roleAdmin.getPermissionList().add(p2);
    System.out.println("roleAdmin.getId() 1: " + roleAdmin.getId());
    GenericDao gdaoi2 = new GenericDao(RoleEntity.class, PersistenceManager.getEmf());
    gdaoi2.createEntity(roleAdmin);
    System.out.println("roleAdmin.getId() 2: " + roleAdmin.getId());
    // define user
    DefaultPasswordService passwordService = new DefaultPasswordService();
    final UserEntity user = new UserEntity();
    user.setUsername("admin");
    user.setPassword(passwordService.encryptPassword("admin"));
    user.getRoleList().add(roleAdmin);
    GenericDao gdaoi3 = new GenericDao(UserEntity.class, PersistenceManager.getEmf());
    gdaoi3.createEntity(user);
  }

  public void testPermissions() {
    UserEntity user = UserQueries.findUserByUsername("admin");
    System.out.println("roles " + user.getRoleList().toString());
    for (RoleEntity role : user.getRoleList()) {
      for (Iterator iterator = role.getPermissionList().iterator(); iterator.hasNext(); ) {
        PermissionEntity permission = (PermissionEntity) iterator.next();
        System.out.println(permission.getPermissionName());
      }
    }
  }
}
