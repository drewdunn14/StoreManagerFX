/**
 * Used to hold connection objects for
 */
public class StoreManager {

    private static StoreManager instance = null;

    private RemoteDataAdapter dao;

    private ProductView productView = null;

    public ProductView getProductView() {
        return productView;
    }

    private ProductController productController = null;

    private LoginController loginController = null;

    public static StoreManager getInstance() {
        if (instance == null)
            instance = new StoreManager("SQLite");
        return instance;
    }


    private StoreManager(String db) {
        // do some initialization here!!!
        dao = new RemoteDataAdapter();
        dao.connect();
        //loginController = new LoginController(dao);
    }






}
