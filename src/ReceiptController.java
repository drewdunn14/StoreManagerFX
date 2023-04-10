import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

    private Stage stage;
    private Scene scene;

    public Order order = null;
    DataAccess myDAO;
    private User loadedUser = null;

    private double subtotal = 0;

    @FXML
    private Button continueButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label userIDLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderIDLabel;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label helloLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toBuyerScene(event);
            }
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toLoginScene(event);
            }
        });

    }


    public void toBuyerScene(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("checkout.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Parent root = loader.getRoot();

        scene = new Scene(root);
        stage.setScene(scene);

        CheckoutController checkoutController = loader.getController();
        checkoutController.setRDA(myDAO);
        checkoutController.setLoadedUser(loadedUser);

        stage.show();
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


    public void setRDA(DataAccess dao) {
        this.myDAO = dao;
    }

    public void setOrder(Order orderIn) {
            this.order = orderIn;
    }

    public void setLoadedUser(User userIn) {
        this.loadedUser = userIn;
    }

    public void setSubtotal(double subtotalIn) {
        this.subtotal = subtotalIn;
    }

    public void initData() {

        helloLabel.setText("Hi " + loadedUser.displayName + ",");

        userIDLabel.setText(loadedUser.userName);

        orderDateLabel.setText(order.getDate());

        orderIDLabel.setText(String.valueOf(order.getOrderID()));

        subtotalLabel.setText(NumberFormat.getCurrencyInstance().format(this.subtotal));

        taxLabel.setText(NumberFormat.getCurrencyInstance().format(order.getTotalTax()));

        totalLabel.setText(NumberFormat.getCurrencyInstance().format(order.getTotalCost()));

    }

}
