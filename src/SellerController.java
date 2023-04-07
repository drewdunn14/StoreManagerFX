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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SellerController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label viewText;

    @FXML
    private Button backButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField quantityText;

    @FXML
    private Label errorText;

    @FXML
    private Label statusText;

    DataAccess myDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toLoginScene(event);
            }
        });

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadProductAndDisplay(event);
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveProduct(event);
            }
        });

    }

    public SellerController() {
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


    private void loadProductAndDisplay(ActionEvent event) {
        try {
            int productID = Integer.parseInt(idText.getText().trim());
            ProductModel productModel = myDAO.loadProduct(productID);
            statusText.setText("");
            if (productModel == null)
                errorText.setText("Invalid Product ID:" + " \"" + productID + "\"");
            else {
                errorText.setText("");
                nameText.setText(productModel.name);
                priceText.setText(String.valueOf(productModel.price));
                quantityText.setText(String.valueOf(productModel.quantity));
            }

        }
        catch (NumberFormatException ex) {
            errorText.setText("Invalid Product ID format.");
            ex.printStackTrace();
        }
    }


    private void saveProduct(ActionEvent event) {
        ProductModel productModel = new ProductModel();

        try {
            errorText.setText("");
            int productID = Integer.parseInt(idText.getText().trim());
            productModel.productID = productID;
            productModel.name = nameText.getText();
            productModel.price = Double.parseDouble(priceText.getText());
            productModel.quantity = Double.parseDouble(quantityText.getText());

            myDAO.saveProduct(productModel);
            statusText.setText("Product Saved.");


        }
        catch (NumberFormatException ex) {
            statusText.setText("Invalid Number Format.");
            ex.printStackTrace();
        }
    }




    public void setRDA(DataAccess dao) {
        this.myDAO = dao;
    }


}
