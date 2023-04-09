// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


// Server class
public class DataServer
{
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);



        // running infinite loop for getting
        // client request

        System.out.println("Starting server program!!!");

        int nClients = 0;

        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                nClients++;
                System.out.println("A new client is connected : " + s + " client number: " + nClients);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}




// ClientHandler class
class ClientHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    Gson gson = new Gson();

    DataAccess dao = new SQLiteDataAdapter();



    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        dao.connect();
    }

    @Override
    public void run()
    {
        String received;
        while (true) {
            try {
                // receive the answer from client
                received = dis.readUTF();

                System.out.println("Message from client " + received);

                RequestModel req = gson.fromJson(received, RequestModel.class);

                if (req.code == RequestModel.EXIT_REQUEST) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                ResponseModel res = new ResponseModel();

                //Start of response for loading product
                if (req.code == RequestModel.LOAD_PRODUCT_REQUEST) {

                    int id = Integer.parseInt(req.body);

                    System.out.println("The Client asks for a product with ID = " + id);

                    ProductModel model = dao.loadProduct(id);

                    if (model != null) {
                        res.code = ResponseModel.OK;
                        res.body = gson.toJson(model);
                    }
                }
                //Start of response for saving product
                else if (req.code == RequestModel.SAVE_PRODUCT_REQUEST) {

                    ProductModel model = gson.fromJson(req.body, ProductModel.class);

                    if (model != null) {
                        dao.saveProduct(model);
                        res.code = ResponseModel.OK;
                        res.body = gson.toJson(model);
                    } else {
                        res.code = ResponseModel.DATA_NOT_FOUND;
                        res.body = "";
                    }
                }
                //Start of response for loading user
                else if (req.code == RequestModel.USER_REQUEST) {

                    User input = gson.fromJson(req.body, User.class);

                    User user = dao.loadUser(input);

                    if (user != null) {
                        res.code = ResponseModel.OK;
                        res.body = gson.toJson(user);
                    } else {
                        res.code = ResponseModel.DATA_NOT_FOUND;
                        res.body = "";
                    }
                }

                //Start of response for requesting an orderID
                else if (req.code == RequestModel.ORDER_ID_REQUEST) {

                    int OrderID = dao.requestOrderID();

                    if (OrderID != 0) {
                        res.code = ResponseModel.OK;
                        res.body = String.valueOf(OrderID);
                    }



                //Start of response for saving an order
                } else if (req.code == RequestModel.ORDER_REQUEST) {

                    System.out.println(req.body);

                    Order order = gson.fromJson(req.body, Order.class);

                    if (order != null) {
                        dao.saveOrder(order);
                        res.code = ResponseModel.OK;
                        res.body = "";
                    }

                } else {
                    res.code = ResponseModel.UNKNOWN_REQUEST;
                    res.body = "";
                }

                String json = gson.toJson(res);
                System.out.println("JSON object of ResponseModel: " + json);


                dos.writeUTF(json);
                dos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
