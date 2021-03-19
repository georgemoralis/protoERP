/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 *  19/03/2021 - (gmoralis) - Added function to retrieve invoices from mydata
 */
package gr.codebb.protoerp.mydata;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.MaskerPane;

public class MyDataMainView implements Initializable {

  @FXML private StackPane mainStackPane;

  private MaskerPane masker = new MaskerPane();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mainStackPane.getChildren().add(masker);
    masker.setVisible(false);
  }

  @FXML
  private void ownMyDataRetrieveAction(ActionEvent event) {
    loadRetrieveService();
  }

  private void loadRetrieveService() {
    masker.setVisible(true);
    masker.setText("Ανάκτηση στοιχείων απο mydata\nΠαρακαλώ περιμένετε");
    Task<String> service =
        new Task<String>() {
          @Override
          protected String call() throws Exception {

            var client = HttpClient.newHttpClient();
            var request =
                HttpRequest.newBuilder()
                    .GET()
                    .uri(
                        URI.create("https://mydatapi.aade.gr/myDATA/RequestTransmittedDocs?mark=0"))
                    .header("aade-user-id", "")
                    .header("Ocp-Apim-Subscription-Key", "")
                    .build();
            System.out.println("HERE");
            HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

            // print response headers
            HttpHeaders headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

            // print status code
            System.out.println(response.statusCode());

            // print response body
            System.out.println(response.body());
            return null;
          }
        };
    service.setOnSucceeded(
        new EventHandler<WorkerStateEvent>() {
          @Override
          public void handle(WorkerStateEvent t) {
            masker.setVisible(false);
          }
        });
    service.setOnFailed(
        new EventHandler<WorkerStateEvent>() {
          @Override
          public void handle(WorkerStateEvent t) {
            masker.setVisible(false);
            System.out.println(service.getValue());
          }
        });
    Thread thread = new Thread(service);
    thread.start();
  }
}
