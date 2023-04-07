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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckoutController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private TextField idText;

    @FXML
    private Button orderButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private TextField quantityText;

    @FXML
    private TableView<ProductModel> orderTableView;

    @FXML
    private TableColumn<ProductModel, Integer> idTableColumn;

    @FXML
    private TableColumn<ProductModel, String> nameTableColumn;

    @FXML
    private TableColumn<ProductModel, Double> priceTableColumn;

    @FXML
    private TableColumn<ProductModel, Double> quantityTableColumn;

    @FXML
    private TableColumn<Double, Double> costTableColumn;


    DataAccess myDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toLoginScene(event);
            }
        });


    }



    public void toLoginScene(ActionEvent event) {
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
        loginController.setRDA(myDAO);

        stage.show();
    }


    private void addProductToTable() {
        // TODO - work with adapters to implement methods to update the table.
    }



    public void setRDA(DataAccess dao) {
        this.myDAO = dao;
    }


}