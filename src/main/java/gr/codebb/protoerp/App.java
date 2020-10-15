/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 10/10/2020 (georgemoralis) - Added preloader
 * 09/10/2020 (georgemoralis) - Loading database connection info from database.xml
 * 09/10/2020 (georgemoralis) - Calling createEntityManager at startup
 * 06/10/2020 (georgemoralis) - Set application title
 * 05/10/2020 (georgemoralis) - Creation of main window
 * 04/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp;

import static gr.codebb.lib.util.ThreadUtil.runAndWait;

import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.protoerp.preloader.PrototypePreloader;
import gr.codebb.util.database.DatabaseDefaultFile;
import gr.codebb.util.database.DatabasesFileCont;
import gr.codebb.util.database.Dbms;
import gr.codebb.util.database.Mysql;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.MasterDetailPane;

public class App extends Application {

  private static DatabasesFileCont currentdatabase = null;
  private Dbms database = null;

  @Override
  public void init() throws Exception {
    super.init();
    currentdatabase = DatabaseDefaultFile.get_instance().readDatabaseFile("protoerp");
    if (currentdatabase == null) {
      try {
        runAndWait(
            () -> {
              AlertDlg.create()
                  .type(AlertDlg.Type.ERROR)
                  .message("Δεν μπορεί να φορτωθεί το database.xml")
                  .title("Πρόβλημα")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();
            });
      } catch (InterruptedException | ExecutionException ex) {
        ex.printStackTrace();
      }
      System.exit(0);
    }
    switch (currentdatabase.getDbmsName()) {
      case "mysql":
        database =
            new Mysql(
                currentdatabase.getHost(),
                currentdatabase.getPort(),
                currentdatabase.getDbname(),
                currentdatabase.getUsername(),
                currentdatabase.getPassword());
        break;
      default:
        {
          try {
            runAndWait(
                () -> {
                  AlertDlg.create()
                      .type(AlertDlg.Type.ERROR)
                      .message("To DMBS που φορτώθηκε δεν υποστηρίζεται")
                      .title("Πρόβλημα")
                      .owner(null)
                      .modality(Modality.APPLICATION_MODAL)
                      .showAndWait();
                });

          } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
          }
          System.exit(0);
        }
        break;
    }
    Map databaseProperties = database.getDatabaseProperties();

    try {
      PersistenceManager.createEntityManager(databaseProperties);
    } catch (Exception e) {
      e.printStackTrace();
      try {
        runAndWait(
            () -> {
              AlertDlg.create()
                  .type(AlertDlg.Type.ERROR)
                  .message("Η σύνδεση με την βάση δεδομένων απέτυχε.\nΤο πρόγραμμα θα τερματιστεί.")
                  .title("Πρόβλημα")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();
            });
      } catch (InterruptedException | ExecutionException ex) {
        ex.printStackTrace();
      }
      System.exit(0);
    }
    if (!InstallDatabaseUpdates.checkDatabaseForUpdates(database)) {
      try {
        runAndWait(
            () -> {
              AlertDlg.create()
                  .type(AlertDlg.Type.ERROR)
                  .message("Η σύνδεση με την βάση δεδομένων απέτυχε.\nΤο πρόγραμμα θα τερματιστεί.")
                  .title("Πρόβλημα")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();
            });

      } catch (InterruptedException | ExecutionException ex) {
        ex.printStackTrace();
      }
      System.exit(0);
    }
  }

  @Override
  public void start(Stage stage) throws Exception {
    FxmlUtil.LoadResult<MainAppView> getMainView = FxmlUtil.load("/fxml/generic/MainApp.fxml");
    FxmlUtil.LoadResult<LeftSideMenuView> getSideMenuView =
        FxmlUtil.load("/fxml/generic/LeftSideMenu.fxml");
    BorderPane menu = new BorderPane();
    menu.setCenter(getSideMenuView.getParent());
    getSideMenuView
        .getController()
        .setMainDetachPane(getMainView.getController().getMainDetachPane());

    MasterDetailPane masterDetailPane =
        new MasterDetailPane(Side.LEFT, getMainView.getParent(), menu, true);
    masterDetailPane.setDividerPosition(0.191);
    masterDetailPane
        .getStylesheets()
        .add(App.class.getResource("/styles/bootstrap3.css").toExternalForm());

    getMainView.getController().setMasterPane(masterDetailPane);

    masterDetailPane.setMinSize(1168.0, 784.0);

    final Scene scene = new Scene(masterDetailPane);

    stage.setTitle(MainSettings.getInstance().getAppNameWithVersion());
    // Application icons
    Image image = new Image("/img/protoerp.png");
    stage.getIcons().addAll(image);
    scene.getStylesheets().add("/styles/bootstrap3.css");
    stage.setScene(scene);
    stage.sizeToScene();
    stage.toFront();

    stage.setFullScreenExitHint("Πατήστε ESC για να βγείτε από την κατάσταση πλήρης οθόνης");
    stage.show();
  }

  public static void main(String[] args) {
    System.setProperty("javafx.preloader", PrototypePreloader.class.getCanonicalName());
    Application.launch(App.class, args);
  }
}
