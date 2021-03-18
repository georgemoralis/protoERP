/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 18/03/2020 (gmoralis) - Προσθήκη μενού για την αποθήκευση των mydata κωδικών
 * 12/03/2020 (gmoralis) - Statusbar for switching companies and users
 * 12/03/2020 (gmoralis) - CompanySelector closing window closes application
 * 02/02/2020 (gmoralis) - Added menu and mitroo password specific menu
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

import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.DialogExceptionHandler;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.lib.util.StageUtil;
import gr.codebb.protoerp.generic.CompanySelectView;
import gr.codebb.protoerp.generic.DatabaseConnectionView;
import gr.codebb.protoerp.generic.LeftSideMenuView;
import gr.codebb.protoerp.generic.MainAppView;
import gr.codebb.protoerp.generic.NewVersionView;
import gr.codebb.protoerp.preloader.PrototypePreloader;
import gr.codebb.protoerp.settings.SettingsHelper;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.internetSettings.MitrooPassView;
import gr.codebb.protoerp.userManagement.CustomSecurityRealm;
import gr.codebb.protoerp.userManagement.LoginView;
import gr.codebb.util.database.DatabaseDefaultFile;
import gr.codebb.util.database.DatabasesFileCont;
import gr.codebb.util.database.Dbms;
import gr.codebb.util.database.Mysql;
import gr.codebb.util.version.VersionUtil;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

    MenuBar menuBar = new MenuBar();
    generateMenu(menuBar);
    statusBar = new StatusBar();

    BorderPane borderPane = new BorderPane();
    borderPane.setTop(menuBar);
    borderPane.setCenter(masterDetailPane);
    borderPane.setBottom(statusBar);

    generateStatusBar();
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

  public void generateMenu(MenuBar menuBar) {
    Menu menu1 = new Menu("Επιλογές");

    menuBar.getMenus().add(menu1);

    // Menu menu2 = new Menu("Συντήρηση");
    // MenuItem menuItem1 = new MenuItem("Κωδικοί υπηρεσιών");
    // menu2.getItems().add(menuItem1);
    Menu maintence_menu = new Menu("Συντήρηση");
    Menu maintence_subMenu_codes = new Menu("Κωδικοί υπηρεσιών");
    MenuItem mitroo_codes = new MenuItem("Κωδικοί Μητρώου");
    maintence_subMenu_codes.getItems().add(mitroo_codes);
    MenuItem mydata_codes = new MenuItem("Κωδικοί Mydata");
    maintence_subMenu_codes.getItems().add(mydata_codes);
    maintence_menu.getItems().add(maintence_subMenu_codes);
    menuBar.getMenus().add(maintence_menu);

    mitroo_codes.setOnAction(
        e -> {
          FxmlUtil.LoadResult<MitrooPassView> getDetailView =
              FxmlUtil.load("/fxml/settings/internetServices/MitrooPass.fxml");
          Alert alert =
              AlertDlgHelper.saveDialog(
                  "Κωδικοί Μητρώου", getDetailView.getParent(), menuBar.getScene().getWindow());
          getDetailView.getController().companyLoad();
          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
            if (getDetailView.getController() != null) {
              getDetailView.getController().save();
            }
          }
        });
    mydata_codes.setOnAction(
        e -> {
          FxmlUtil.LoadResult<MitrooPassView> getDetailView =
              FxmlUtil.load("/fxml/settings/internetServices/MyDataPass.fxml");
          Alert alert =
              AlertDlgHelper.saveDialog(
                  "Κωδικοί MyData", getDetailView.getParent(), menuBar.getScene().getWindow());
          // getDetailView.getController().companyLoad();
          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
            if (getDetailView.getController() != null) {
              // getDetailView.getController().save();
            }
          }
        });
  }

  public void generateStatusBar() {
    statusBar.setText("");
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    CompanyEntity selected = (CompanyEntity) session.getAttribute("company");
    Button companyButton =
        new Button(selected.getName().substring(0, 10)); // get only 10 first letters
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
              .setOnCloseRequest(
                  new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                      System.exit(0);
                    }
                  });
          stagecompany
              .getScene()
              .getWindow()
              .setOnHiding(
                  new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                      Subject currentUser = SecurityUtils.getSubject();
                      Session session = currentUser.getSession();
                      CompanyEntity selected = (CompanyEntity) session.getAttribute("company");
                      companyButton.setText(selected.getName().substring(0, 10));
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
              .setOnCloseRequest(
                  new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                      System.exit(0);
                    }
                  });
          loginstage
              .getScene()
              .getWindow()
              .setOnHiding(
                  new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                      Subject currentUser = SecurityUtils.getSubject();
                      Session session = currentUser.getSession();
                      userButton.setText((String) session.getAttribute("username"));
                    }
                  });
          loginstage.showAndWait();
        });
  }
}
