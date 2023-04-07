import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


public class LoginController implements Initializable {


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
            if (foundUser.isManager) {
                System.out.println("User is manager!");

            }
        }
    }



}
