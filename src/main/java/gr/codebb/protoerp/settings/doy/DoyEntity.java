/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 25/02/2021 (gmoralis) - Initial commit
 */
package gr.codebb.protoerp.settings.doy;

import gr.codebb.lib.crud.intf.Displayable;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doy")
public class DoyEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String code;
  @Getter @Setter private String name;

  @Getter @Setter private Boolean active;

  @Override
  public String getComboDisplayValue() {
    return getCode() + " - " + getName();
  }
}
