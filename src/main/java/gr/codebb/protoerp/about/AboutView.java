/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 22/04/2021 (gmoralis) - Updated to 2021
 * 11/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.about;

import gr.codebb.protoerp.MainSettings;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class AboutView implements Initializable {

  @FXML private GridPane vbox;
  @FXML private TextArea textArea;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    textArea.setText(getAboutText());
  }

  private String getAboutText() {

    StringBuilder text =
        getVersionParagraph()
            .append(getBuildInfoParagraph())
            .append(getJavaParagraph())
            .append(getOsParagraph())
            .append("Copyright (c) 2013-2021, codebb")
            .append(
                "\n\nΓια πληροφορίες,ενημερώσεις,αναβαθμίσεις εγγραφείτε στο forum της codebb\n")
            .append("http://codebb.gr/forum/");

    return text.toString();
  }

  /** @treatAsPrivate */
  public String getBuildJavaVersion() {
    return MainSettings.getInstance().getBuildJavaVersion();
  }

  /** @treatAsPrivate */
  public String getBuildInfo() {
    return MainSettings.getInstance().getAppName();
  }

  private StringBuilder getVersionParagraph() {
    StringBuilder sb = new StringBuilder();
    sb.append(MainSettings.getInstance().getAppNameWithVersion()) // NOI18N
        .append("\n\n"); // NOI18N
    return sb;
  }

  private StringBuilder getBuildInfoParagraph() {
    StringBuilder sb = new StringBuilder("Πληροφορίες έκδοσης");
    sb.append("\n")
        .append(MainSettings.getInstance().getVersion())
        .append("\n") // NOI18N
        .append("Ημερομηνία ")
        .append(MainSettings.getInstance().getBuildDateTime())
        .append("\n")
        .append("Έκδοση Java ")
        .append(MainSettings.getInstance().getBuildJavaVersion())
        .append("\n\n"); // NOI18N
    return sb;
  }

  private StringBuilder getJavaParagraph() {
    StringBuilder sb = new StringBuilder("Java\n"); // NOI18N
    sb.append(System.getProperty("java.runtime.version"))
        .append(", ") // NOI18N
        .append(System.getProperty("java.vendor"))
        .append("\n\n"); // NOI18N
    return sb;
  }

  private StringBuilder getOsParagraph() {
    StringBuilder sb = new StringBuilder("Λειτουργικό σύστημα");
    sb.append("\n")
        .append(System.getProperty("os.name"))
        .append(", ") // NOI18N
        .append(System.getProperty("os.arch"))
        .append(", ") // NOI18N
        .append(System.getProperty("os.version"))
        .append("\n\n"); // NOI18N
    return sb;
  }
}
