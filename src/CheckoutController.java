import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button addProductButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private TextField quantityText;

    @FXML
    private TextField subtotalText;

    @FXML
    private TextField taxText;

    @FXML
    private TextField totalCostText;


    private DoubleProperty subtotalOrderCost = new SimpleDoubleProperty(0);

    @FXML
    public ObservableList<ProductModelBinded> checkoutList = FXCollections.observableArrayList();

    @FXML
    private TableView<ProductModelBinded> orderTableView = new TableView<ProductModelBinded>();

    @FXML
    private TableColumn<ProductModelBinded, Integer> idTableColumn;

    @FXML
    private TableColumn<ProductModelBinded, String> nameTableColumn;

    @FXML
    private TableColumn<ProductModelBinded, Double> priceTableColumn;

    @FXML
    private TableColumn<ProductModelBinded, Double> quantityTableColumn;

    @FXML
    private TableColumn<ProductModelBinded, Double> costTableColumn;


    DataAccess myDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        orderTableView.setPlaceholder(new Label("Nothing in your cart yet..."));

        idTableColumn.setCellValueFactory(new PropertyValueFactory<ProductModelBinded, Integer>("productID"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<ProductModelBinded, String>("name"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<ProductModelBinded, Double>("price"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<ProductModelBinded, Double>("quantity"));
        costTableColumn.setCellValueFactory(new PropertyValueFactory<ProductModelBinded, Double>("cost"));
        orderTableView.setItems(checkoutList);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toLoginScene(event);
            }
        });


        addProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addToTable(event);
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



    private void addToTable(ActionEvent event) {
        int productID = Integer.parseInt(idText.getText().trim());
        ProductModel productModel = myDAO.loadProduct(productID);
        if (productModel != null) {
            double quantityEntered = Double.parseDouble(quantityText.getText().trim());
            if (quantityEntered < productModel.quantity) {

                productModel.quantity -= quantityEntered;
                myDAO.saveProduct(productModel);
                ProductModelBinded productModelBinded = new ProductModelBinded(productModel.productID, productModel.name, productModel.price, quantityEntered);
                setSubtotalOrderCost(getSubtotalOrderCost() + productModelBinded.getCost());
                checkoutList.add(productModelBinded);
                subtotalText.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(subtotalOrderCost.get())));
                totalCostText.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(subtotalOrderCost.get() * 1.09)));



            }
        }
    }


    public DoubleProperty subtotalOrderCostProperty() {
        return subtotalOrderCost;
    }
    public double getSubtotalOrderCost() {
        return subtotalOrderCost.get();
    }
    public void setSubtotalOrderCost(double value) {
        subtotalOrderCost.set(value);
    }

    public void setRDA(DataAccess dao) {
        this.myDAO = dao;
    }


}