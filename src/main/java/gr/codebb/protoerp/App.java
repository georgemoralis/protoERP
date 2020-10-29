/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 29/10/2020 (georgemoralis) - Added login window
 * 20/10/2020 (georgemoralis) - Added setDefaultUncaughtExceptionHandler
 * 15/10/2020 (georgemoralis) - Check if client is up to date
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
import gr.codebb.lib.util.DialogExceptionHandler;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.lib.util.StageUtil;
import gr.codebb.protoerp.generic.DatabaseConnectionView;
import gr.codebb.protoerp.generic.LeftSideMenuView;
import gr.codebb.protoerp.generic.MainAppView;
import gr.codebb.protoerp.generic.NewVersionView;
import gr.codebb.protoerp.preloader.PrototypePreloader;
import gr.codebb.protoerp.settings.SettingsHelper;
import gr.codebb.protoerp.userManagement.CustomSecurityRealm;
import gr.codebb.protoerp.userManagement.LoginView;
import gr.codebb.util.database.DatabaseDefaultFile;
import gr.codebb.util.database.DatabasesFileCont;
import gr.codebb.util.database.Dbms;
import gr.codebb.util.database.Mysql;
import gr.codebb.util.version.VersionUtil;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.controlsfx.control.MasterDetailPane;

public class App extends Application {

  public static DatabasesFileCont currentdatabase = null;
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
              FxmlUtil.LoadResult<DatabaseConnectionView> databaseWindow =
                  FxmlUtil.load("/fxml/generic/DatabaseConnection.fxml");
              Stage stage =
                  StageUtil.setStageSettings(
                      "Ρύθμισεις Βάσης Δεδομένων",
                      new Scene(databaseWindow.getParent()),
                      Modality.APPLICATION_MODAL,
                      null,
                      null,
                      "/img/protoerp.png");
              stage.setResizable(false);
              stage.showAndWait();
            });
      } catch (InterruptedException | ExecutionException ex) {
        ex.printStackTrace();
      }
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
    checkClientUpToDate();
  }

  @Override
  public void start(Stage stage) throws Exception {
    /** load apache shiro */
    DefaultSecurityManager securityManager = new DefaultSecurityManager();
    securityManager.setRealm(new CustomSecurityRealm());
    SecurityUtils.setSecurityManager(securityManager);

    FxmlUtil.LoadResult<LoginView> loginWindow = FxmlUtil.load("/fxml/userManagement/Login.fxml");
    Stage loginstage =
        StageUtil.setStageSettings(
            MainSettings.getInstance().getAppNameWithVersion() +" Σύνδεση χρήστη",
            new Scene(loginWindow.getParent()),
            Modality.APPLICATION_MODAL,
            null,
            null,
            "/img/protoerp.png");
    loginstage.setResizable(false);
    loginstage
        .getScene()
        .getWindow()
        .setOnCloseRequest(
            new EventHandler<WindowEvent>() {
              @Override
              public void handle(WindowEvent we) {
                System.exit(0);
              }
            });
    loginstage.showAndWait();

    /** Main stage loading */
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

  public void checkClientUpToDate() {
    String version = SettingsHelper.loadStringSetting("product_version");
    if (version == null) // no version found add the existing one
    {
      SettingsHelper.addStringSetting("product_version", MainSettings.getInstance().getVersion());
      return;
    }
    // check if version is equal
    if (VersionUtil.isVersionEquals(
        MainSettings.getInstance().getVersion(),
        SettingsHelper.loadStringSetting("product_version"))) {
      System.out.println("Versions are equal");
      return;
    }
    // database has newer version stored so client is old
    if (VersionUtil.isComparedVersionNewer(
        MainSettings.getInstance().getVersion(),
        SettingsHelper.loadStringSetting("product_version"))) {
      try {
        runAndWait(
            () -> {
              AlertDlg.create()
                  .type(AlertDlg.Type.ERROR)
                  .message(
                      "O client χρειάζεται αναβάθμιση για να τρέξει με την υπάρχον βάση.\nΑναβαθμίστε τον client και επανεκκινήστε το πρόγραμμα.")
                  .title("Πρόβλημα")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();
              System.exit(0);
            });
      } catch (InterruptedException | ExecutionException ex) {
        ex.printStackTrace();
      }
    } // update to latest version
    else {
      SettingsHelper.updateStringSetting(
          "product_version", MainSettings.getInstance().getVersion());
      try {
        runAndWait(
            () -> {
              FxmlUtil.LoadResult<NewVersionView> versionWindow =
                  FxmlUtil.load("/fxml/generic/NewVersionInfo.fxml");
              Stage stage =
                  StageUtil.setStageSettings(
                      "Νέα έκδοση!",
                      new Scene(versionWindow.getParent()),
                      Modality.APPLICATION_MODAL,
                      null,
                      null,
                      "/img/protoerp.png");
              stage.setResizable(false);
              stage.setAlwaysOnTop(true);
              stage.showAndWait();
            });
      } catch (InterruptedException | ExecutionException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    Thread.setDefaultUncaughtExceptionHandler(new DialogExceptionHandler());
    System.setProperty("javafx.preloader", PrototypePreloader.class.getCanonicalName());
    Application.launch(App.class, args);
  }
}
