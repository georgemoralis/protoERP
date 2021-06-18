/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.auth.util;

import eu.taxofficer.protoerp.auth.entities.PermissionEntity;
import eu.taxofficer.protoerp.auth.entities.RoleEntity;
import eu.taxofficer.protoerp.auth.entities.UserEntity;
import eu.taxofficer.protoerp.auth.queries.UserQueries;
import java.util.HashSet;
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
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/** @author George Moralis */
public class CustomAuthorizingRealm extends AuthorizingRealm {

  public DefaultPasswordService passwordService = new DefaultPasswordService();

  public CustomAuthorizingRealm() {
    this(new AllowAllCredentialsMatcher());
  }

  public CustomAuthorizingRealm(final CredentialsMatcher matcher) {
    super(matcher);
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    UserEntity current = (UserEntity) (principals.getPrimaryPrincipal());
    UserEntity user = UserQueries.findUserByUsername(current.getUsername());
    if (user == null) {
      return null;
    }

    Set<String> roles = new HashSet<>();
    Set<String> permissions = new HashSet<>();
    for (RoleEntity role : user.getRoles()) {
      roles.add(role.getName());
      for (PermissionEntity perm : role.getPermissions()) {
        permissions.add(perm.getPermissionName());
      }
    }
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
    authorizationInfo.setStringPermissions(permissions);
    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at)
      throws AuthenticationException {
    UsernamePasswordToken upat = (UsernamePasswordToken) at;
    UserEntity user = UserQueries.findUserByUsername(upat.getUsername());
    if (user != null && passwordService.passwordsMatch(upat.getPassword(), user.getPassword())) {
      return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    } else {
      throw new AuthenticationException("Μη έγκυρος συνδυασμός ονόματος/κωδικού χρήστη");
    }
  }
}
