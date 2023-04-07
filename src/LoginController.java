import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField userID;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label incorrectLabel;

    DataAccess myDAO;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getLoginText(event);
            }
        });

    }

    public LoginController() {
    }

    public void getLoginText(ActionEvent event) {
        String username = userID.getText().trim();
        String passWord = password.getText().trim();

        User user = new User();
        user.userID = 0;
        user.userName = username;
        user.password = passWord;
        user.displayName = "";
        user.isManager = false;

        System.out.println("Login with username = " + username + " and password = " + passWord);

        User foundUser = myDAO.loadUser(user);

        if (foundUser != null) {
            System.out.println("User found!");
            incorrectLabel.setText("");
            if (foundUser.isManager) {
                System.out.println("User is manager!");
                toSellerScene(event);
            } else {
                toBuyerScene(event);
            }
        } else {
            incorrectLabel.setText("Incorrect UserID/Password");
        }
    }


    public void toSellerScene(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("seller.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Parent root = loader.getRoot();

        scene = new Scene(root);
        stage.setScene(scene);

        SellerController sellerController = loader.getController();
        sellerController.setRDA(myDAO);

        stage.show();
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

        stage.show();
    }




    public void setRDA(DataAccess dao) {
        this.myDAO = dao;
    }





}
