/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 02/03/2021 (georgemoralis) - WIP Work on mitroo retrieve data
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.webserv.mitroo.MitrooService;
import gr.codebb.webserv.mitroo.ResponsedMitrooData;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.controlsfx.control.MaskerPane;

public class CompanyView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private VBox mainVBox;
  @FXML private TabPane tabPane;
  @FXML private CbbClearableTextField textName;
  @FXML private CbbClearableTextField textJob;
  @FXML private CbbClearableTextField textVatNumber;
  @FXML private CbbClearableTextField textDoy;
  @FXML private CbbClearableTextField textEmail;
  @FXML private CbbClearableTextField textMobilePhone;

  private MaskerPane masker = new MaskerPane();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mainStackPane.getChildren().add(masker);
    masker.setVisible(false);
  }

  @FXML
  private void onTaxisUpdate(ActionEvent event) {
    loadService();
  }

  private void loadService() {
    masker.setVisible(true);
    masker.setText("Ανάκτηση στοιχείων απο gsis\nΠαρακαλώ περιμένετε");
    Task<String> service =
        new Task<String>() {
          @Override
          protected String call() throws Exception {
            try {
              // Create a trust manager that does not validate certificate chains
              final TrustManager[] trustAllCerts =
                  new TrustManager[] {
                    new X509TrustManager() {
                      @Override
                      public void checkClientTrusted(
                          final X509Certificate[] chain, final String authType) {}

                      @Override
                      public void checkServerTrusted(
                          final X509Certificate[] chain, final String authType) {}

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
            ResponsedMitrooData returnValue =
                sc.getData("", "", textVatNumber.getText(), "", new Date());
            if ((returnValue.getErrordescr() == null) || (returnValue.getErrordescr().isEmpty())) {
              textName.setText(returnValue.getName());
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
}
