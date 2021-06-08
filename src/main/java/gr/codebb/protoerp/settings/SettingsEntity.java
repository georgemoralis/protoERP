/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 13/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.settings;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_settings")
public class SettingsEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String settingName;

  @Getter @Setter private String settingValue;

  @Getter @Setter private int companyId;

  @PrePersist
  private void onCreate() {
    if (companyId == 0) {
      companyId = -1; // -1 means that setting is for all companies
    }
  }
}
