/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 19/02/2021 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.settings.tableSettings;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_tableSettings")
public class TableSettingsEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String tableKey;

  @Getter @Setter private String tableValue;
}
