/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
 /*
 * Changelog
 * =========
 * 09/03/2021 (georgemoralis) - Added controlfx validation (todo change it with validatorFX sometime later)
 * 07/03/2021 (georgemoralis) - Added plant button works . Improvements
 * 05/03/2021 (georgemoralis) - Merging plants here
 * 04/03/2021 (georgemoralis) - Added saving of company plants as well
 * 04/03/2021 (georgemoralis) - Added saving of new company
 * 03/03/2021 (georgemoralis) - Loading of common passwords
 * 02/03/2021 (georgemoralis) - Added doy combo and loading of it
 * 02/03/2021 (georgemoralis) - WIP Work on mitroo retrieve data
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.crud.cellFactory.CheckBoxFactory;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.protoerp.settings.SettingsHelper;
import gr.codebb.protoerp.settings.countries.CountriesQueries;
import gr.codebb.protoerp.settings.doy.DoyEntity;
import gr.codebb.protoerp.settings.doy.DoyQueries;
import gr.codebb.protoerp.settings.internetSettings.MitrooPassView;
import gr.codebb.protoerp.util.validation.CustomValidationDecoration;
import gr.codebb.protoerp.util.validation.Validators;
import gr.codebb.protoerp.util.validation.VatValidator;
import gr.codebb.webserv.mitroo.MitrooService;
import gr.codebb.webserv.mitroo.ResponsedCompanyKad;
import gr.codebb.webserv.mitroo.ResponsedMitrooData;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;

public class CompanyView implements Initializable {

    @FXML
    private StackPane mainStackPane;
    @FXML
    private VBox mainVBox;
    @FXML
    private TabPane tabPane;

    @FXML
    @TextFieldProperty(type = TextFieldProperty.Type.STRING)
    private CbbClearableTextField textName;

    @FXML
    @TextFieldProperty(type = TextFieldProperty.Type.STRING)
    private CbbClearableTextField textJob;

    @FXML
    @TextFieldProperty(type = TextFieldProperty.Type.STRING)
    private CbbClearableTextField textVatNumber;

    @FXML
    @TextFieldProperty(type = TextFieldProperty.Type.STRING)
    private CbbClearableTextField textEmail;

    @FXML
    @TextFieldProperty(type = TextFieldProperty.Type.STRING)
    private CbbClearableTextField textMobilePhone;

    @FXML
    @TextFieldProperty(type = TextFieldProperty.Type.STRING)
    private CbbClearableTextField textRegisteredName;

    private MaskerPane masker = new MaskerPane();
    @FXML
    private SearchableComboBox<DoyEntity> doyCombo;

    private ValidationSupport validation;
    private final DetailCrud<CompanyEntity> detailCrud = new DetailCrud<>(this);

    @FXML
    private StackPane mainStackPane1;
    @FXML
    private Button newPlantButton;
    @FXML
    private Button openPlantButton;
    @FXML
    private Button deletePlantButton;
    @FXML
    private TableView<PlantsEntity> tablePlants;
    @FXML
    private TableColumn<PlantsEntity, Long> columnId;
    @FXML
    private TableColumn<PlantsEntity, Boolean> columnActive;
    @FXML
    private TableColumn<PlantsEntity, Integer> columnCode;
    @FXML
    private TableColumn<PlantsEntity, String> columnDescription;

    private ObservableList<PlantsEntity> plantrow;
    CompanyEntity company;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainStackPane.getChildren().add(masker);
        masker.setVisible(false);
        new ComboboxService<>(DoyQueries.getDoyDatabase(true), doyCombo).start();
        DisplayableListCellFactory.setComboBoxCellFactory(doyCombo);

        // plants table
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));
        columnActive.setCellFactory(new CheckBoxFactory<PlantsEntity>().cellFactory);
        openPlantButton
                .disableProperty()
                .bind(Bindings.isEmpty(tablePlants.getSelectionModel().getSelectedItems()));
        deletePlantButton
                .disableProperty()
                .bind(Bindings.isEmpty(tablePlants.getSelectionModel().getSelectedItems()));
        EventHandler plantDoubleMouseClick
                = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                    openPlantAction(null);
                }
            }
        };

        tablePlants.addEventHandler(MouseEvent.MOUSE_PRESSED, plantDoubleMouseClick);
        Platform.runLater(
                () -> {
                    this.validation = new ValidationSupport();
                    this.validation.setValidationDecorator(new CustomValidationDecoration());
                    Validators.createValidators();
                    registerValidators();
                });
        plantrow = FXCollections.observableArrayList();
        plantrow.addListener(
                new ListChangeListener<PlantsEntity>() {
            @Override
            public void onChanged(
                    javafx.collections.ListChangeListener.Change<? extends PlantsEntity> c) {
                // System.out.println("Changed on " + c);//c.getFrom -> line that did the change
                if (c.next()) {
                    tablePlants.setItems(plantrow);
                }
            }
        });
    }

    private void registerValidators() {
        validation.registerValidator(
                textName,
                Validators.combine(
                        Validators.notEmptyValidator(), Validators.onlyLettersValidator(Severity.WARNING)));
        validation.registerValidator(textJob, false, Validators.onlyLettersValidator(Severity.WARNING));
        validation.registerValidator(
                textMobilePhone, false, Validators.onlyNumbersValidator(Severity.WARNING));
        validation.registerValidator(doyCombo, Validators.notEmptyValidator());
        validation.registerValidator(
                textEmail,
                Validators.combine(
                        Validators.notEmptyValidator(), Validators.emailValidator(Severity.ERROR)));
        validation.registerValidator(
                textVatNumber,
                Validators.combine(
                        Validators.notEmptyValidator(),
                        Validators.onlyNumbersValidator(Severity.ERROR),
                        new VatValidator()));

        
    }

    public void initNewCompany() {
        company = new CompanyEntity();

    }

    @FXML
    private void onTaxisUpdate(ActionEvent event) {
        if (SettingsHelper.loadStringSetting("mitroo_username") == null
                || SettingsHelper.loadStringSetting("mitroo_username").isEmpty()) {
            ButtonType response
                    = AlertDlg.create()
                            .message(
                                    "Δεν βρέθηκαν κωδικοι για τη εφαρμογή του μητρώου\nΘέλετε να αποθηκεύσετε τώρα?")
                            .title("Αποθήκευση κωδικών")
                            .owner(masker.getScene().getWindow())
                            .modality(Modality.APPLICATION_MODAL)
                            .showAndWaitConfirm();
            if (response == ButtonType.OK) {
                FxmlUtil.LoadResult<MitrooPassView> getDetailView
                        = FxmlUtil.load("/fxml/settings/internetServices/MitrooPass.fxml");
                Alert alert
                        = AlertDlgHelper.saveDialog(
                                "Κωδικοί Μητρώου", getDetailView.getParent(), masker.getScene().getWindow());
                Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                getDetailView.getController().commonLoad();
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (getDetailView.getController() != null) {
                        getDetailView.getController().save();
                    }
                }
            } else {
                return;
            }
        }
        loadService();
    }

    private void loadService() {
        masker.setVisible(true);
        masker.setText("Ανάκτηση στοιχείων απο gsis\nΠαρακαλώ περιμένετε");
        Task<String> service
                = new Task<String>() {
            @Override
            protected String call() throws Exception {
                try {
                    // Create a trust manager that does not validate certificate chains
                    final TrustManager[] trustAllCerts
                            = new TrustManager[]{
                                new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(
                                    final X509Certificate[] chain, final String authType) {
                            }

                            @Override
                            public void checkServerTrusted(
                                    final X509Certificate[] chain, final String authType) {
                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                        }
                            };

                    // Install the all-trusting trust manager
                    final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                    // Create an ssl socket factory with our all-trusting manager
                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                    HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                MitrooService sc = new MitrooService();
                // use the TLSv1.2 protocol
                System.setProperty("https.protocols", "TLSv1.2");
                // call to web service
                ResponsedMitrooData returnValue
                        = sc.getData(
                                SettingsHelper.loadStringSetting("mitroo_username"),
                                SettingsHelper.loadStringSetting("mitroo_password"),
                                textVatNumber.getText(),
                                SettingsHelper.loadStringSetting("mitroo_reprvat"),
                                new Date());
                if ((returnValue.getErrordescr() == null) || (returnValue.getErrordescr().isEmpty())) {
                    textName.setText(returnValue.getName());
                    Platform.runLater(
                            () -> {
                                doyCombo
                                        .getSelectionModel()
                                        .select(DoyQueries.getDoyByCode(returnValue.getDoyCode()));
                            });
                    PlantsEntity p = new PlantsEntity();
                    p.setCode(0);
                    p.setDescription("Ἐδρα");
                    p.setAddress(returnValue.getAddress());
                    p.setCity(returnValue.getAddressArea());
                    p.setTk(returnValue.getTk());
                    p.setActive(true);
                    p.setArea("");
                    p.setFax("");
                    p.setPhone("");
                    p.setCountry(CountriesQueries.getCountryByCode("GR"));
                    // kirios kadi
                    for (ResponsedCompanyKad kad : returnValue.getDrastir()) {
                        if (kad.getEidosDescr().matches("ΚΥΡΙΑ")) {
                            textJob.setText(kad.getPerigrafi());
                            plantrow.add(p);
                        }
                    }
                } else {
                    return returnValue.getErrorcode() + ":" + returnValue.getErrordescr();
                }
                return null;
            }
        };
        service.setOnSucceeded(
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                masker.setVisible(false);
                if (service.getValue() != null) // error occured
                {
                    String[] msg = service.getValue().split(":");
                    AlertDlg.create()
                            .type(AlertDlg.Type.ERROR)
                            .message(msg[1])
                            .title(msg[0])
                            .owner(masker.getScene().getWindow())
                            .modality(Modality.APPLICATION_MODAL)
                            .showAndWait();
                }
            }
        });
        service.setOnFailed(
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                masker.setVisible(false);
                if (service.getValue() != null) // error occured
                {
                    String[] msg = service.getValue().split(":");
                    AlertDlg.create()
                            .type(AlertDlg.Type.ERROR)
                            .message(msg[1])
                            .title(msg[0])
                            .owner(masker.getScene().getWindow())
                            .modality(Modality.APPLICATION_MODAL)
                            .showAndWait();
                }
            }
        });
        Thread thread = new Thread(service);
        thread.start();
    }

    public boolean validateControls() {
        if (validation.isInvalid()) {
            Validators.showValidationResult(validation);
            AlertDlg.create()
                    .type(AlertDlg.Type.ERROR)
                    .message("Ελέξτε την φόρμα για λάθη")
                    .title("Πρόβλημα")
                    .owner(doyCombo.getScene().getWindow())
                    .modality(Modality.APPLICATION_MODAL)
                    .showAndWait();
            return false;
        }
        return true;
    }

    public void fillData(CompanyEntity e) {

        detailCrud.loadModel(e);
        doyCombo.getSelectionModel().select(e.getDoy());
        for (PlantsEntity a : e.getPlantLines()) {
            plantrow.add(a);
        }
    }

    public void SaveNewCompany() {
        GenericDao gdao = new GenericDao(CompanyEntity.class, PersistenceManager.getEmf());
        detailCrud.saveModel(new CompanyEntity());
        CompanyEntity company = detailCrud.getModel();
        company.setDoy(doyCombo.getSelectionModel().getSelectedItem());
        company.setActive(true); // when creating it is true!
        plantrow.forEach(
                (plantpos) -> {
                    company.addPlantLine(plantpos);
                });
        CompanyEntity saved = (CompanyEntity) gdao.createEntity(company);
    }

    @FXML
    private void newPlantAction(ActionEvent event) {
        FxmlUtil.LoadResult<PlantsDetailView> getDetailView
                = FxmlUtil.load("/fxml/settings/company/PlantsDetailView.fxml");
        Alert alert
                = AlertDlgHelper.saveDialog(
                        "Προσθήκη Κεντρικόυ-υποκαταστήματος",
                        getDetailView.getParent(),
                        mainStackPane.getScene().getWindow());
        Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okbutton.addEventFilter(
                ActionEvent.ACTION,
                (event1) -> {
                    if (!getDetailView.getController().validateControls()) {
                        event1.consume();
                    }
                });
        if (plantrow.isEmpty()) { // if no rows create edra
            getDetailView.getController().setNewPlant(true);
        } else {
            getDetailView.getController().setNewPlant(false);
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (getDetailView.getController() != null) {
                PlantsEntity p = getDetailView.getController().saveNewPlant();
                plantrow.add(p);
            }
        }
    }

    @FXML
    private void openPlantAction(ActionEvent event) {
        FxmlUtil.LoadResult<PlantsDetailView> getDetailView
                = FxmlUtil.load("/fxml/settings/company/PlantsDetailView.fxml");
        Alert alert
                = AlertDlgHelper.editDialog(
                        "Άνοιγμα/Επεξεργασία Κεντρικόυ-υποκαταστήματος",
                        getDetailView.getParent(),
                        mainStackPane.getScene().getWindow());
        getDetailView.getController().fillData(tablePlants.getSelectionModel().getSelectedItem());
        Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okbutton.addEventFilter(
                ActionEvent.ACTION,
                (event1) -> {
                    if (!getDetailView.getController().validateControls()) {
                        event1.consume();
                    }
                });
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (getDetailView.getController() != null) {
                int row = tablePlants.getSelectionModel().getSelectedIndex();
                PlantsEntity selected = tablePlants.getSelectionModel().getSelectedItem();
                PlantsEntity p = getDetailView.getController().saveEdit(plantrow.get(row));
                plantrow.remove(selected);
                plantrow.add(p);
            }
        }
    }

    @FXML
    private void deletePlantAction(ActionEvent event) {
        ButtonType response
                = AlertDlg.create()
                        .message(
                                "Είστε σιγουροι ότι θέλετε να διαγράψετε την εγκατάσταση : \n"
                                + tablePlants.getSelectionModel().getSelectedItem().getDescription())
                        .title("Διαγραφή")
                        .modality(Modality.APPLICATION_MODAL)
                        .owner(tablePlants.getScene().getWindow())
                        .showAndWaitConfirm();
        if (response == ButtonType.OK) {
            PlantsEntity selected = tablePlants.getSelectionModel().getSelectedItem();
            plantrow.remove(selected);
        }
    }
}
