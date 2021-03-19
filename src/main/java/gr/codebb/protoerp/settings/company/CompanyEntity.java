/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 19/03/2021 (gmoralis) - Προσθήκη πεδίων για υπηρεσία mydata
 * 16/03/2021 (gmoralis) - Προσθήκη κωδικών μητρώου για εταιρία
 * 04/03/2021 (gmoralis) - Added company plants
 * 04/03/2021 (gmoralis) - Added extra fields
 * 25/02/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.protoerp.settings.doy.DoyEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
public class CompanyEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String name;
  @Getter @Setter private String registeredName;
  @Getter @Setter private String job;
  @Getter @Setter private String vatNumber;

  @ManyToOne
  @JoinColumn(name = "doy_id")
  @Getter
  @Setter
  private DoyEntity doy;

  @Getter @Setter private String email;
  @Getter @Setter private String mobilePhone;
  @Getter @Setter private Boolean active;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  @Getter
  private List<PlantsEntity> plantLines = new ArrayList<>();

  public void addPlantLine(PlantsEntity line) {
    plantLines.add(line);
    line.setCompany(this);
  }

  public void removePlantLine(PlantsEntity line) {
    plantLines.remove(line);
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
    mitroo_username = "";
    mitroo_password = "";
    mitroo_vatRepresentant = "";
    userMyData = "";
    passMyData = "";
    demoUserMyData = "";
    demoPassMyData = "";
    demoMyDataEnabled = false;
  }
}
