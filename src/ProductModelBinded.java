import javafx.beans.property.*;

public class ProductModelBinded {

    //IntegerProperty to emulate "productID" of ProductModel class.
    private IntegerProperty productID = new SimpleIntegerProperty();

    //StringProperty to emulate "name" of ProductModel class.
    private StringProperty name = new SimpleStringProperty();

    //DoubleProperty to emulate "price" of ProductModel class.
    private DoubleProperty price = new SimpleDoubleProperty();

    //DoubleProperty to emulate "quantity" of ProductModel class.
    private DoubleProperty quantity = new SimpleDoubleProperty();

    ////DoubleProperty to calculate the requested quantity of a certain product multiplied by its unit price.
    private DoubleProperty cost = new SimpleDoubleProperty();

    public ProductModelBinded(int productIDIn, String nameIn, double priceIn, double quantityIn) {
        setProductID(productIDIn);
        setName(nameIn);
        setPrice(priceIn);
        setQuantity(quantityIn);
        setCost(price.get() * quantity.get());
    }

    // start of getter/setter methods for productID
    public IntegerProperty productIDProperty() {
        return productID;
    }
    public int getProductID() {
        return productID.get();
    }
    public void setProductID(int value) {
        productID.set(value);
    }

    // start of getter/setter methods for name.
    public StringProperty nameProperty() {
        return name;
    }
    public String getName() {
        return name.get();
    }
    public void setName(String value) {
        name.set(value);
    }

    // start of getter/setter methods for price.
    public DoubleProperty priceProperty() {
        return price;
    }
    public double getPrice() {
        return price.get();
    }
    public void setPrice(double value) {
        price.set(value);
    }

    // start of getter/setter methods for quantity.
    public DoubleProperty quantityProperty() {
        return quantity;
    }
    public double getQuantity() {
        return quantity.get();
    }
    public void setQuantity(double value) {
        quantity.set(value);
    }


    // start of getter/setter methods for cost.
    public DoubleProperty costProperty() {
        return cost;
    }
    public double getCost() {
        return cost.get();
    }
    public void setCost(double value) {
        cost.set(value);
    }

}
