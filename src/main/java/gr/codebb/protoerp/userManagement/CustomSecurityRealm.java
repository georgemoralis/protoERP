/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 26/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.userManagement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class CustomSecurityRealm extends AuthorizingRealm {

  public DefaultPasswordService passwordService = new DefaultPasswordService();

  public CustomSecurityRealm() {
    this(new AllowAllCredentialsMatcher());
  }

  public CustomSecurityRealm(final CredentialsMatcher matcher) {
    super(matcher);
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    Set roles = new HashSet<>();
    Set permissions = new HashSet<>();

    Collection<UserEntity> principalsList = principals.byType(UserEntity.class);
    for (UserEntity user : principalsList) {
      roles.addAll(user.getRolesName());
      for (RolesEntity role : user.getRoleList()) {
        for (Iterator iterator = role.getPermissionList().iterator(); iterator.hasNext(); ) {
          PermissionsEntity permission = (PermissionsEntity) iterator.next();
          permissions.add(new WildcardPermission(permission.getPermissionName()));
        }
      }
    }

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
    info.setRoles(roles);
    info.setObjectPermissions(permissions);

    return info;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    UsernamePasswordToken upat = (UsernamePasswordToken) token;
    UserEntity user = UserQueries.findUserByUsername(upat.getUsername());
    if (user != null && passwordService.passwordsMatch(upat.getPassword(), user.getPassword())) {
      return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    } else {
      throw new AuthenticationException("Μη έγκυρος συνδυασμός ονόματος/κωδικού χρήστη");
    }
  }
}
