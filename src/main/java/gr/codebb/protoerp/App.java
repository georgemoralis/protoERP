/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 08/04/2021 (gmoralis) - Clear tabs if you change company or user
 * 08/04/2021 (gmoralis) - Confirm before exiting
 * 02/04/2021 (gmoralis) - Προσθήκη GR locale
 * 29/03/2021 (gmoralis) - Μεταφορά μενού σε fxml στην MainMenuView class
 * 19/03/2021 (gmoralis) - Ολοκλήρωση mydata κωδικών αποθήκευσης
 * 18/03/2021 (gmoralis) - Προσθήκη μενού για την αποθήκευση των mydata κωδικών
 * 12/03/2021 (gmoralis) - Statusbar for switching companies and users
 * 12/03/2021 (gmoralis) - CompanySelector closing window closes application
 * 02/02/2021 (gmoralis) - Added menu and mitroo password specific menu
 * 06/12/2020 (gmoralis) - Use DEBUG variable to show debug messages
 * 13/11/2020 (gmoralis) - Enabled hibernate.enable_lazy_load_no_trans
 * 29/10/2020 (gmoralis) - Added login window
 * 20/10/2020 (gmoralis) - Added setDefaultUncaughtExceptionHandler
 * 15/10/2020 (gmoralis) - Check if client is up to date
 * 10/10/2020 (gmoralis) - Added preloader
 * 09/10/2020 (gmoralis) - Loading database connection info from database.xml
 * 09/10/2020 (gmoralis) - Calling createEntityManager at startup
 * 06/10/2020 (gmoralis) - Set application title
 * 05/10/2020 (gmoralis) - Creation of main window
 * 04/10/2020 (gmoralis) - Initial commit
 */
package gr.codebb.protoerp;

import static gr.codebb.lib.util.ThreadUtil.runAndWait;
import static javafx.geometry.Orientation.VERTICAL;

import eu.taxofficer.protoerp.company.entities.CompanyEntity;
import gr.codebb.ctl.CbbDetachableTabPane;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AlertHelper;
import gr.codebb.lib.util.DialogExceptionHandler;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.lib.util.StageUtil;
import gr.codebb.protoerp.generic.CompanySelectView;
import gr.codebb.protoerp.generic.DatabaseConnectionView;
import gr.codebb.protoerp.generic.LeftSideMenuView;
import gr.codebb.protoerp.generic.MainAppView;
import gr.codebb.protoerp.generic.MainMenuView;
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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.StatusBar;

public class App extends Application {

  public static DatabasesFileCont currentdatabase = null;
  private Dbms database = null;
  private StatusBar statusBar;

  @Override
  public void init() throws Exception {
    super.init();
    Locale.setDefault(MainSettings.getInstance().getApplocale());
    if (!MainSettings.DEBUG) // disable logging in no debug mode
    {
      Logger.getLogger("com.zaxxer.hikari.pool.PoolBase").setLevel(Level.INFO);
      Logger.getLogger("com.zaxxer.hikari.pool.HikariPool").setLevel(Level.INFO);
      Logger.getLogger("com.zaxxer.hikari.HikariDataSource").setLevel(Level.INFO);
      Logger.getLogger("com.zaxxer.hikari.HikariConfig").setLevel(Level.INFO);
      Logger.getLogger("com.zaxxer.hikari.util.DriverDataSource").setLevel(Level.INFO);
    }

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
    // custom databaseproperties
    databaseProperties.put("hibernate.enable_lazy_load_no_trans", "true");
    if (MainSettings.DEBUG) {
      databaseProperties.put("hibernate.show_sql", "true");
      databaseProperties.put("hibernate.format_sql", "true");
    }
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
            MainSettings.getInstance().getAppNameWithVersion() + " Σύνδεση χρήστη",
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

    // Company Selector
    FxmlUtil.LoadResult<CompanySelectView> CompanySelector =
        FxmlUtil.load("/fxml/generic/CompanySelector.fxml");
    Stage stagecompany =
        StageUtil.setStageSettings(
            "Επιλογή Εταιρίας",
            new Scene(CompanySelector.getParent()),
            Modality.APPLICATION_MODAL,
            null,
            null,
            "/img/protoerp.png");
    stagecompany.setResizable(false);
    stagecompany
        .getScene()
        .getWindow()
        .setOnCloseRequest(
            new EventHandler<WindowEvent>() {
              @Override
              public void handle(WindowEvent we) {
                System.exit(0);
              }
            });
    stagecompany.showAndWait();

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

    FxmlUtil.LoadResult<MainMenuView> getMenuBar = FxmlUtil.load("/fxml/generic/MainMenu.fxml");
    getMenuBar.getController().setMainDetachPane(getMainView.getController().getMainDetachPane());

    statusBar = new StatusBar();
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((MenuBar) getMenuBar.getParent());
    borderPane.setCenter(masterDetailPane);
    borderPane.setBottom(statusBar);

    generateStatusBar(getMainView.getController().getMainDetachPane());
    final Scene scene = new Scene(borderPane);

    stage.setTitle(MainSettings.getInstance().getAppNameWithVersion());
    // Application icons
    Image image = new Image("/img/protoerp.png");
    stage.getIcons().addAll(image);
    scene.getStylesheets().add("/styles/bootstrap3.css");
    stage.setScene(scene);
    stage.sizeToScene();
    stage.toFront();

    stage.setFullScreenExitHint("Πατήστε ESC για να βγείτε από την κατάσταση πλήρης οθόνης");

    stage.setOnCloseRequest(
        (WindowEvent we) -> {
          Optional<ButtonType> response =
              AlertHelper.exitConfirm(
                  stage.getScene().getWindow(), "Θελετέ να τερματίσετε την εφαρμογή?");
          if (response.get() == ButtonType.CANCEL) {
            we.consume();
          }
        });
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
    System.setProperty("liquibase.databaseChangeLogTableName", "protoerp_databasechangelog");
    System.setProperty(
        "liquibase.databaseChangeLogLockTableName", "protoerp_databasechangeloglock");
    Thread.setDefaultUncaughtExceptionHandler(new DialogExceptionHandler());
    System.setProperty("javafx.preloader", PrototypePreloader.class.getCanonicalName());
    Application.launch(App.class, args);
  }

  public void generateStatusBar(CbbDetachableTabPane mainDetachPane) {
    statusBar.setText("");
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    CompanyEntity selected = (CompanyEntity) session.getAttribute("company");
    Button companyButton = new Button(selected.getName());
    statusBar.getLeftItems().add(companyButton);
    statusBar.getLeftItems().add(new Separator(VERTICAL));
    // user
    Button userButton = new Button((String) session.getAttribute("username"));
    statusBar.getLeftItems().add(userButton);

    companyButton.setOnAction(
        e -> {
          // Company Selector
          FxmlUtil.LoadResult<CompanySelectView> CompanySelector =
              FxmlUtil.load("/fxml/generic/CompanySelector.fxml");
          Stage stagecompany =
              StageUtil.setStageSettings(
                  "Επιλογή Εταιρίας",
                  new Scene(CompanySelector.getParent()),
                  Modality.APPLICATION_MODAL,
                  null,
                  null,
                  "/img/protoerp.png");
          stagecompany.setResizable(false);
          stagecompany
              .getScene()
              .getWindow()
              .setOnHiding(
                  (WindowEvent we) -> {
                    Subject currentUser1 = SecurityUtils.getSubject();
                    Session session1 = currentUser1.getSession();
                    CompanyEntity selected1 = (CompanyEntity) session1.getAttribute("company");
                    String currentText = companyButton.getText();
                    companyButton.setText(selected1.getName());
                    if (!companyButton
                        .getText()
                        .equals(currentText)) // if we change company clear or tabs
                    {
                      mainDetachPane.getTabs().clear();
                    }
                  });
          stagecompany.setOnCloseRequest(
              (WindowEvent we) -> {
                Optional<ButtonType> response =
                    AlertHelper.exitConfirm(
                        stagecompany.getScene().getWindow(), "Θελετέ να τερματίσετε την εφαρμογή?");
                if (response.get() == ButtonType.CANCEL) {
                  we.consume();
                } else {
                  System.exit(0);
                }
              });
          stagecompany.showAndWait();
        });
    userButton.setOnAction(
        e -> {
          currentUser.logout();
          FxmlUtil.LoadResult<LoginView> loginWindow =
              FxmlUtil.load("/fxml/userManagement/Login.fxml");
          Stage loginstage =
              StageUtil.setStageSettings(
                  MainSettings.getInstance().getAppNameWithVersion() + " Σύνδεση χρήστη",
                  new Scene(loginWindow.getParent()),
                  Modality.APPLICATION_MODAL,
                  null,
                  null,
                  "/img/protoerp.png");
          loginstage.setResizable(false);
          loginstage
              .getScene()
              .getWindow()
              .setOnHiding(
                  (WindowEvent we) -> {
                    Subject currentUser1 = SecurityUtils.getSubject();
                    Session session1 = currentUser1.getSession();
                    String currentBUser = userButton.getText();
                    userButton.setText((String) session1.getAttribute("username"));
                    if (!currentBUser.equals(
                        userButton.getText())) // if we change user clear all tabs
                    {
                      mainDetachPane.getTabs().clear();
                    }
                  });
          loginstage.setOnCloseRequest(
              (WindowEvent we) -> {
                Optional<ButtonType> response =
                    AlertHelper.exitConfirm(
                        loginstage.getScene().getWindow(), "Θελετέ να τερματίσετε την εφαρμογή?");
                if (response.get() == ButtonType.CANCEL) {
                  we.consume();
                } else {
                  System.exit(0);
                }
              });
          loginstage.showAndWait();
        });
  }
}
