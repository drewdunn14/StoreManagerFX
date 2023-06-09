import java.sql.*;

public class SQLiteDataAdapter implements DataAccess {
    Connection conn = null;

    @Override
    public void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:store.db";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(url);

            if (conn == null)
                System.out.println("Cannot make the connection!!!");
            else
                System.out.println("The connection object is " + conn);

            System.out.println("Connection to SQLite has been established.");

            /* Test data!!!
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");

            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            */

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveProduct(ProductModel product) {
        try {
            Statement stmt = conn.createStatement();

            if (loadProduct(product.productID) == null) {           // this is a new product!
                stmt.execute("INSERT INTO Product(productID, name, price, quantity) VALUES ("
                        + product.productID + ","
                        + '\'' + product.name + '\'' + ","
                        + product.price + ","
                        + product.quantity + ")"
                );
            }
            else {
                stmt.executeUpdate("UPDATE Product SET "
                        + "productID = " + product.productID + ","
                        + "name = " + '\'' + product.name + '\'' + ","
                        + "price = " + product.price + ","
                        + "quantity = " + product.quantity +
                        " WHERE productID = " + product.productID
                );

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE ProductID = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.productID = rs.getInt(1);
                product.name = rs.getString(2);
                product.price = rs.getDouble(3);
                product.quantity = rs.getDouble(4);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }

    @Override
    public User loadUser(User user) {
        User registeredUser = new User();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE UserName = ? AND Password = ?");
            statement.setString(1, user.userName);
            statement.setString(2, user.password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                registeredUser.userID = resultSet.getInt("UserID");
                registeredUser.userName = resultSet.getString("UserName");
                registeredUser.password = resultSet.getString("Password");
                registeredUser.displayName = resultSet.getString("DisplayName");
                registeredUser.isManager = resultSet.getBoolean("IsManager");
                resultSet.close();
                statement.close();

                return registeredUser;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int requestOrderID() {
        try {
            int lastOrderID = 0;
            Statement findLastOrderID = conn.createStatement();
            ResultSet resultSet = findLastOrderID.executeQuery("SELECT MAX(OrderID) FROM Orders");
            if (resultSet.next()) {
                lastOrderID = resultSet.getInt(1);
            }
            return (lastOrderID + 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public void saveOrder(Order order) throws SQLException {

        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Orders VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, order.getOrderID());
            statement.setString(2, order.getDate());
            statement.setString(3, order.getCustomerName());
            statement.setDouble(4, order.getTotalCost());
            statement.setDouble(5, order.getTotalTax());

            statement.execute();    // commit to the database;
            statement.close();

            statement = conn.prepareStatement("INSERT INTO OrderLine VALUES (?, ?, ?, ?)");
            for (OrderLine line: order.getLines()) { // store for each order line!
                statement.setInt(1, order.getOrderID());
                statement.setInt(2, line.getProductID());
                statement.setDouble(3, line.getQuantity());
                statement.setDouble(4, line.getCost());

                statement.execute();    // commit to the database;
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
