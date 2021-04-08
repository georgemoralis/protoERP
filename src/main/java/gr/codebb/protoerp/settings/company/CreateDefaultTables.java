/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.mydata.masterdata.MasterDataQueries;
import gr.codebb.protoerp.tables.measurementUnits.MeasurementUnitsEntity;
import gr.codebb.protoerp.tables.vat.VatEntity;
import java.math.BigDecimal;

/** @author snow */
public class CreateDefaultTables {

  public void createEntries(CompanyEntity company) {
    createVatEntries(company);
    createMeasureEntries(company);
  }

  public void createVatEntries(CompanyEntity company) {
    GenericDao gdao = new GenericDao(VatEntity.class, PersistenceManager.getEmf());
    VatEntity vat1 = new VatEntity();
    vat1.setActive(true);
    vat1.setDescription("ΚΑΝΟΝΙΚΟ");
    vat1.setVatRate(new BigDecimal(24.00));
    vat1.setCompany(company);
    vat1.setMydata_vat(MasterDataQueries.getmdVatFromVatRate(new BigDecimal(24.00)));
    gdao.createEntity(vat1);

    VatEntity vat2 = new VatEntity();
    vat2.setActive(true);
    vat2.setDescription("ΜΕΙΩΜΕΝΟ");
    vat2.setVatRate(new BigDecimal(13.00));
    vat2.setCompany(company);
    vat2.setMydata_vat(MasterDataQueries.getmdVatFromVatRate(new BigDecimal(13.00)));
    gdao.createEntity(vat2);

    VatEntity vat3 = new VatEntity();
    vat3.setActive(true);
    vat3.setDescription("ΥΠΕΡΜΕΙΩΜΕΝΟ");
    vat3.setVatRate(new BigDecimal(6.00));
    vat3.setCompany(company);
    vat3.setMydata_vat(MasterDataQueries.getmdVatFromVatRate(new BigDecimal(6.00)));
    gdao.createEntity(vat3);

    VatEntity vat4 = new VatEntity();
    vat4.setActive(true);
    vat4.setDescription("ΜΗΔΕΝΙΚΟ");
    vat4.setVatRate(new BigDecimal(0.00));
    vat4.setCompany(company);
    vat4.setMydata_vat(MasterDataQueries.getmdVatFromVatRate(new BigDecimal(0.00)));
    gdao.createEntity(vat4);
  }

  public void createMeasureEntries(CompanyEntity company) {
    GenericDao gdao = new GenericDao(MeasurementUnitsEntity.class, PersistenceManager.getEmf());
    MeasurementUnitsEntity un1 = new MeasurementUnitsEntity();
    un1.setActive(true);
    un1.setDescription("Χωρίς");
    un1.setShortName("");
    un1.setCompany(company);
    un1.setMydata_measureUnit(MasterDataQueries.getmdMeasureFromId(1L));
    gdao.createEntity(un1);

    MeasurementUnitsEntity un2 = new MeasurementUnitsEntity();
    un2.setActive(true);
    un2.setDescription("Τεμάχιο");
    un2.setShortName("ΤΜΧ");
    un2.setCompany(company);
    un2.setMydata_measureUnit(MasterDataQueries.getmdMeasureFromId(2L));
    gdao.createEntity(un2);

    MeasurementUnitsEntity un3 = new MeasurementUnitsEntity();
    un3.setActive(true);
    un3.setDescription("Τεμάχιο");
    un3.setShortName("ΤΜΧ");
    un3.setCompany(company);
    un3.setMydata_measureUnit(MasterDataQueries.getmdMeasureFromId(3L));
    gdao.createEntity(un3);
  }
}
