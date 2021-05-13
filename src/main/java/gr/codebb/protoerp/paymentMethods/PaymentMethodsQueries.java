/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 13/05/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.paymentMethods;

import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.util.List;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class PaymentMethodsQueries {

  public static List<PaymentMethodsEntity> getPaymentMethodsDatabase(boolean activeonly) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<PaymentMethodsEntity> results;
    if (activeonly) {
      results =
          streams
              .streamAll(em, PaymentMethodsEntity.class)
              .where(c -> c.getActive())
              .where(p -> p.getCompany().equals(current))
              .toList();
    } else {
      results =
          streams
              .streamAll(em, PaymentMethodsEntity.class)
              .where(p -> p.getCompany().equals(current))
              .toList();
    }

    em.close();
    return results;
  }
}
