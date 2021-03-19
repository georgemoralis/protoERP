/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 19/03/2021 (gmoralis) - Added getCurrentCompany method
 */
package gr.codebb.protoerp.settings.company;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class CompanyUtil {

  public static CompanyEntity getCurrentCompany() {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    return (CompanyEntity) session.getAttribute("company");
  }
}
