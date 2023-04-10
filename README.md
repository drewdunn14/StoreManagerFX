# COMP 3700 - Project 1 Documentation #
## Preface ##
In this document, I will be providing documentation about all things related to my Project 1.
This includes but will not be limited to...
- Database Design
- Project Architecture
- Use Cases (Buyer/Seller/Login)
- What is in each tier?
- What is in each view?
- What does each controller do?
- Additions and Improvements since Project1

Lastly, when describing the use cases of the project, I will be taking a sequential approach to\
describe all classes/functions/logic being invoked all the way from the start of runtime to the closing/exit of the application.

## IDE/Compiler Details ##
This entire project was developed in within the IntelliJ IDEA Community, and was compiled using the tools included in the IDE.
- Project SDK: Amazon Corretto Version 1.8.0_362
- JDBC Jar Version: sqlite-jdbc-3.16.1
- Gson Jar Version: gson-2.9.0
- IMPORTANT: Ensure that the SQLite & Gson JAR files are added to the project structure!
- IMPORTANT: Ensure that 'Server' run configuration is invoked before 'Client' run configuration.

Critical Detail: This project utilizes a SDK kit that has the JavaFX JAR embedded already,
most Java 8 (aka 1.8) SDKs will be structured this way. Please use such an SDK, if you choose
not to you must configure a JavaFX Jar file in your project structure. I heavily recommend not
doing this as JavaFX was disbanded by Oracle after Java 8/1.8 and finding a compatible JavaFX JAR file
can be a pain.

## Database Design ##
The backend of this entire project utilizes an SQLite database, and uses the Java SQL package to communicate with this database.
### SQL Tables ###
This project utilizes four unique tables apart from the default "sqlite_sequence" table.
#### User ####
- Coordinates with the LoginController Class to verify user identification.
#### Product ####
- Coordinates with ProductController & CheckoutController to store data about available items for purchase, their price, and the quantity of them available.
#### Orders ####
- Coordinates with CheckoutController to store data related to users purchasing products, the date purchased, their total cost, and the total tax as well.
#### OrderLine ####
- Coordinates with CheckoutController to store data related to the contents of an order.

## Project Architecture ##
This project utilizes a Model-View-Controller and a 3-tier design pattern, as well as incorporates the client/server architecture.

### Model-View-Controller VS. 3-Tier ###
It is very important to differentiate these two design patterns, as they can often be confusing.\
\
A 3-tier architecture consists of a UI (presentation tier), a business/application tier, and a data tier.\
The big benefit of this 3-tier architecture is that although each tier may communicate with another tier, 
the idea is that each of these layers is their own entity, and serves a dedicated purpose.

MVC (Model-View-Controller) is also a design pattern that relies on isolating certain components,
but is much more concerned about separating the way information is processed and presented a user/client.

### Model-View-Controller ###
There are three unique Model-View-Controller design patterns embedded within this application.
- **User - Login.fxml - LoginController**
- **Product - Seller.fxml - SellerController**
- **Order - Checkout.fxml - CheckoutController**

### 3-Tier Architecture ###
**So what is in each tier?**
- Presentation Tier: Utilizes the JavaFX JAR/package to present information and options to the user.
- Application Tier: Utilizes a specified controller in tandem with a class called "RemoteDataAdapter" to communicate requests/responses with the server.
Let it be clear that the RemoteDataAdapter's purpose is to serve the interest (requests) of the end-user/client.
The server (DataServer/ClientHandler) connects to the same port as this adapter and starts a thread to accept data from it.
This 'ClientHandler' (which is handling the communication thread) specifies what to do with each request received from the RemoteDataAdapter. 
If it needs to read/write data to a database, it relies on SQLiteDataAdapter which will do the heavy lifting of interacting with the database and ensuring that all data
being read/written is formatted in such a way to maintain the integrity of the database.
- Data Tier: Utilizes an SQLite Database to store and provide information to the application.

## View & Screens ##
Within this application, there are three possible screens/views available (not including the static ones).
To see what controller they communicate with, refer to the "Model-View-Controller" section above.
As previously mentioned, the presentation layer utilizes JavaFX to build the GUI.
- Login.fxml: prompts user for a username and password to access the system.
- Seller.fxml: allows user (must be a seller) to view existing products available on the database, as well as modify/add product prices & quantities.
- Checkout.fxml: allows user (must be a buyer) to add products to their cart, and has an interactive display to log everything the user is buying.

## Controllers ##
As previously mentioned in the MVC section, there are three controllers\
that are utilized to effectively communicate and accept requests from the user and generate responses from the server.

It is worth mentioning that every controller will have an instance of RemoteDataAdapter. As previously mentioned,
this adapter is responsible for taking input from the user, and generating requests to send to the server.
Without this class, these controllers wouldn't be very functional, and for sure could not communicate with the server.
TL;DR: RemoteDataAdapter enables communication from the client/controller to the server. 

1) LoginController
- Serves as controller for login.fxml
- responsible for parsing username/password and making a request to server via RemoteDataAdapter to verify the attributes of a user.
- responsible for dictating whether a user is sent to the seller or buyer view based on if the received 'User' object is a manager or not.

2) SellerController
- Serves as controller for seller.fxml
- responsible for loading details of item in SQL Product Table, allowing seller to request edit/add/load products from server via RemoteDataAdapter.

3) CheckoutController
- Serves as controller for checkout.fxml
- responsible for requesting to add product to cart, also interactively updates the tableview that allows user to view items in their cart.
- responsible for requesting to save Orders to "Orders" SQL Table, as well as adding items within that order to "OrderLine" SQL Table,
- This like the rest of the requests, must be done through RemoteDataAdapter communicating with the server.


## Testing ##
Testing took a big chunk of time. My main focus was to focus on the requests and responses between
RemoteDataAdapter <--> ClientHandler <--> SQLiteDataAdapter and verifying their behavior/performance with many different use cases,
and then after being sure they were responding correctly to incorporate their functionality to be available in controllers.
The primary way that I verified behavior was by printing figures/data to the console. This allowed the project to hit all the necessary benchmarks.\
I did not use a testing framework such as jUnit or Jupyter because for the content because for the small changes I had to make to an already 
working codebase could just as easily be seen in output console. Throughout much of the source code, 
there are many instances where a request is made to output data to the console.\
This is how I verified use cases of my tool.

### Improvements from Project 1 / Project 2 Additions ###
1) Implemented Client/Server Architecture
2) Completely rewrote the GUI using a more modern library.
3) Implemented Gson technology to establish communication between client/server.
4) Implemented new Date functionality in Order object, allowing for much more detailed date attribute.
5) Utilized DataAccess interface behavior to ensure that communication protocols on both SQLiteDataAdapter & RemoteDataAdapter matched up.
6) Created a receipt screen that prints details of the Buyer's order object.
7) Implemented Request/Response classes to aid in the communication protocols between Client/Server.
8) Implemented use of binding properties, very useful to establish quick updating of UI.
9) Implemented JavaFx scenes, allowing users to traverse through different views in a much smoother manner.













