/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 13/04/2021 (gmoralis) - name,job are now TEXT in database
 * 13/04/2021 (gmoralis) - Προσθήκης ημερομηνίας έναρξης - διακοπης (αν υπάρχει)
 * 08/04/2021 (gmoralis) - Removed mitrooCommonPassEnabled will do it otherwise
 * 30/03/2021 (gmoralis) - Προσθήκη mitrooCommonPassEnabled για χρήση ή μη κοινων κωδικών μητρώου
 * 19/03/2021 (gmoralis) - Προσθήκη πεδίων για υπηρεσία mydata
 * 16/03/2021 (gmoralis) - Προσθήκη κωδικών μητρώου για εταιρία
 * 04/03/2021 (gmoralis) - Added company plants
 * 04/03/2021 (gmoralis) - Added extra fields
 * 25/02/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.company;

import eu.taxofficer.protoerp.company.entities.CompanyPlantsEntity;
import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.settings.doy.DoyEntity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "company")
public class CompanyEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Column(columnDefinition = "TEXT")
  @Getter
  @Setter
  private String name;

  @Getter @Setter private String registeredName;

  @Column(columnDefinition = "TEXT")
  @Getter
  @Setter
  private String job;

  @Getter @Setter private String vatNumber;

  @ManyToOne
  @JoinColumn(name = "doy_id")
  @Getter
  @Setter
  private DoyEntity doy;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private VatStatus vatStatus;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private CompanyEidos companyEidos;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private CompanyMorfi companyMorfi;

  @Getter @Setter private String email;
  @Getter @Setter private String mobilePhone;
  @Getter @Setter private Boolean active;

  @Getter @Setter private LocalDate dateStarted;
  @Getter @Setter private LocalDate dateEnded;

  @Getter @Setter private Boolean demoCompany;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  @Getter
  private List<CompanyPlantsEntity> plantLines = new ArrayList<>();

  public void addPlantLine(CompanyPlantsEntity line) {
    plantLines.add(line);
    line.setCompany(this);
  }

  public void removePlantLine(CompanyPlantsEntity line) {
    plantLines.remove(line);
    line.setCompany(null);
  }

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  @Getter
  private List<CompanyKadEntity> kadLines = new ArrayList<>();

  public void addKadLine(CompanyKadEntity line) {
    kadLines.add(line);
    line.setCompany(this);
  }

  public void removeKadLine(CompanyKadEntity line) {
    kadLines.remove(line);
    line.setCompany(null);
  }

  // κωδικοί υπηρεσίας μητρώου
  @Getter @Setter private String mitroo_username;
  @Getter @Setter private String mitroo_password;
  @Getter @Setter private String mitroo_vatRepresentant;

  // κωδικοί για το mydata
  @Getter @Setter private String userMyData;
  @Getter @Setter private String passMyData;
  @Getter @Setter private String demoUserMyData;
  @Getter @Setter private String demoPassMyData;
  @Getter @Setter private Boolean demoMyDataEnabled;

  @PrePersist
  private void onCreate() {
    // Κενές τιμές κατα την δημιουργία της εταιριας (για να μην ειναι null στην βάση)
    demoMyDataEnabled = false;
    demoCompany = false;
  }

  @Override
  public String getComboDisplayValue() {
    return name;
  }
}
