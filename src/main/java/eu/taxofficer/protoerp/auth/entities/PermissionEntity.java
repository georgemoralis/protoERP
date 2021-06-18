/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.auth.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** @author George Moralis */
@Entity
@Table(name = "protoerp_permissions")
public class PermissionEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String permissionName;
  @Getter @Setter private String permissionDisplayName;
}
