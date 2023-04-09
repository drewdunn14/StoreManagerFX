import java.sql.SQLException;

public interface DataAccess {
    void connect();

    void saveProduct(ProductModel product);

    ProductModel loadProduct(int productID);

    User loadUser(User user);

    int requestOrderID();

    void saveOrder(Order order) throws SQLException;

}