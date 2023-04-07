import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startLogin;

    public RemoteDataAdapter rda = new RemoteDataAdapter();

    public StartController() {
        rda.connect();
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        startLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toLoginScreen(event);
            }
        });

    }

    public void toLoginScreen(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("login.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Parent root = loader.getRoot();

        scene = new Scene(root);
        stage.setScene(scene);

        LoginController loginController = loader.getController();
        loginController.setRDA(rda);

        stage.show();

    }


}
