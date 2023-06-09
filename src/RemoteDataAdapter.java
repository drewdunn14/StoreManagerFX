import com.google.gson.Gson;
import jdk.nashorn.internal.ir.RuntimeNode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RemoteDataAdapter implements DataAccess {
    Gson gson = new Gson();
    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;

    @Override
    public void connect() {
        try {
            s = new Socket("localhost", 5056);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveProduct(ProductModel product) {
        RequestModel req = new RequestModel();
        req.code = req.SAVE_PRODUCT_REQUEST;
        req.body = gson.toJson(product);

        String json = gson.toJson(req);

        try {
            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            ProductModel model = gson.fromJson(res.body, ProductModel.class);

            if (res.code == ResponseModel.OK) {
                System.out.println(model.name + " successfully saved to database.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        RequestModel req = new RequestModel();
        req.code = req.LOAD_PRODUCT_REQUEST;
        req.body = String.valueOf(productID);

        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            } else         // this is a JSON string for a product information
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a product with that ID!");
                    return null;
                } else {
                    ProductModel model = gson.fromJson(res.body, ProductModel.class);
                    System.out.println("Receiving a ProductModel object");
                    System.out.println("ProductID = " + model.productID);
                    System.out.println("Product name = " + model.name);
                    return model; // found this product and return!!!
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public User loadUser(User user) {
        RequestModel req = new RequestModel();
        req.code = RequestModel.USER_REQUEST;
        req.body = gson.toJson(user);

        String json = gson.toJson(req);
        try {

            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            } else if (res.code == ResponseModel.DATA_NOT_FOUND) {
                System.out.println("The requested user cannot be found in the system.");
                return null;
            } else {
                User foundUser = gson.fromJson(res.body, User.class);
                System.out.println("UserID " + "\"" + foundUser.userID + "\"" + " is verified.");
                return foundUser;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int requestOrderID() {
        RequestModel req = new RequestModel();
        req.code = RequestModel.ORDER_ID_REQUEST;
        req.body = gson.toJson(0);

        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response:" + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return 0;
            } else if (res.code == ResponseModel.OK) {
                int OrderID = gson.fromJson(res.body, int.class);
                return OrderID;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public void saveOrder(Order order) {
        RequestModel req = new RequestModel();
        req.code = req.ORDER_REQUEST;
        req.body = gson.toJson(order);

        String json = gson.toJson(req);

        try {
            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.OK) {
                Order savedOrder = gson.fromJson(res.body, Order.class);
                //System.out.println("OrderID #" + savedOrder.getOrderID() + " has been saved to database." );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




}