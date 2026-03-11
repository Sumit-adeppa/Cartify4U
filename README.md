<h1 align="center">Cartify4U</h1>

<p align="center">
Spring Boot based E-Commerce Backend API with MongoDB, JWT authentication, and Razorpay payment integration.
</p>

<hr>

<h2>Description</h2>

<p>
Cartify4U is a backend application built using Spring Boot.  
The project provides REST APIs for an e-commerce platform including authentication, product management, cart operations, order management, and payment integration using Razorpay.
</p>

<p>
The application uses MongoDB as the database and JWT for secure authentication.
</p>

<hr>

<h2>Features</h2>

<ul>
<li>Spring Boot REST API</li>
<li>JWT authentication and authorization</li>
<li>MongoDB NoSQL database integration</li>
<li>Cart management APIs</li>
<li>Product filtering support</li>
<li>Order management APIs</li>
<li>Razorpay payment gateway integration</li>
<li>Request validation</li>
<li>Docker container support</li>
</ul>

<hr>

<h2>Tech Stack</h2>

<ul>
<li>Java 17</li>
<li>Spring Boot 3</li>
<li>Spring Web</li>
<li>Spring Data MongoDB</li>
<li>Spring Security</li>
<li>JWT (JSON Web Tokens)</li>
<li>MongoDB</li>
<li>Razorpay Java SDK</li>
<li>Maven</li>
<li>Docker and Docker Compose</li>
</ul>

<hr>

<h2>Prerequisites</h2>

<ul>
<li>Java JDK 17 or later</li>
<li>Apache Maven</li>
<li>MongoDB instance</li>
<li>Razorpay account</li>
<li>Docker (optional)</li>
</ul>

<hr>

<h2>Application Configuration</h2>

<p>
Create an <code>application.properties</code> file inside:
</p>

<pre>
src/main/resources/application.properties
</pre>

<p>Add the following configuration values:</p>

<pre>
spring.data.mongodb.uri=&lt;your_mongodb_connection_string&gt;
jwt.secret=&lt;your_jwt_secret&gt;
razorpay.key=&lt;your_razorpay_key&gt;
razorpay.secret=&lt;your_razorpay_secret&gt;
server.port=9090
</pre>

<hr>

<h2>Project Setup</h2>

<h3>Clone the Repository</h3>

<pre>
git clone https://github.com/Sumit-adeppa/Cartify4U.git
cd Cartify4U
</pre>

<h3>Build the Application</h3>

<pre>
mvn clean install
</pre>

<h3>Run the Application</h3>

<pre>
mvn spring-boot:run
</pre>

<h3>Run Using Jar</h3>

<pre>
java -jar target/cartify-0.0.1-SNAPSHOT.jar
</pre>

<hr>

<h2>Docker Setup</h2>

<h3>Build Docker Image</h3>

<pre>
docker build -t cartify4u .
</pre>

<h3>Run Docker Compose</h3>

<pre>
docker-compose up -d
</pre>

<hr>

<h2>Base URL</h2>

<pre>
http://localhost:9090
</pre>

<hr>

<h2>Authentication APIs</h2>

<table border="1">
<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>POST</td>
<td>/api/users/register</td>
<td>Register new user</td>
</tr>

<tr>
<td>POST</td>
<td>/api/auth/login</td>
<td>User login and JWT generation</td>
</tr>

<tr>
<td>POST</td>
<td>/api/auth/logout</td>
<td>User logout</td>
</tr>

</table>

<h3>Register User</h3>

<pre>
POST /api/users/register

{
  "username": "User_Name",
  "email": "User_Mail",
  "password": "Password",
  "role": "CUSTOMER"
}
</pre>

<h3>User Login</h3>

<pre>
POST /api/auth/login

{
  "username": "User_Name",
  "password": "Password"
}
</pre>

<p>
After login the server returns a JWT token.  
Use this token in protected APIs.
</p>

<hr>

<h2>Product APIs</h2>

<table border="1">
<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>GET</td>
<td>/api/products</td>
<td>Get all products</td>
</tr>

<tr>
<td>GET</td>
<td>/api/products?category=Shirts</td>
<td>Filter products by category</td>
</tr>

</table>

<hr>

<h2>Cart APIs</h2>

<table border="1">

<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>GET</td>
<td>/api/cart/items</td>
<td>Get cart items</td>
</tr>

<tr>
<td>GET</td>
<td>/api/cart/items/count?username=USER_NAME</td>
<td>Get cart item count</td>
</tr>

<tr>
<td>POST</td>
<td>/api/cart/add</td>
<td>Add item to cart</td>
</tr>

<tr>
<td>PUT</td>
<td>/api/cart/update</td>
<td>Update cart item quantity</td>
</tr>

<tr>
<td>DELETE</td>
<td>/api/cart/delete</td>
<td>Remove item from cart</td>
</tr>

</table>

<h3>Add Item To Cart</h3>

<pre>
POST /api/cart/add

{
  "username": "USER_NAME",
  "productId": "PRODUCT_ID",
  "quantity": 1
}
</pre>

<h3>Update Cart Item</h3>

<pre>
PUT /api/cart/update

{
  "username": "USER_NAME",
  "productId": "PRODUCT_ID",
  "quantity": 2
}
</pre>

<h3>Delete Cart Item</h3>

<pre>
DELETE /api/cart/delete

{
  "username": "USER_NAME",
  "productId": "PRODUCT_ID"
}
</pre>

<hr>

<h2>Order APIs</h2>

<table border="1">

<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>GET</td>
<td>/api/orders</td>
<td>Get user orders</td>
</tr>

</table>

<hr>

<h2>Payment APIs</h2>

<table border="1">

<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>POST</td>
<td>/api/payment/razorpay</td>
<td>Create Razorpay payment</td>
</tr>

</table>
<b>Note: This API will work in Postman</b>
<hr>

<h2>Environment Variables</h2>

<ul>
<li>MONGO_URI</li>
<li>JWT_SECRET</li>
<li>RAZORPAY_KEY</li>
<li>RAZORPAY_SECRET</li>
</ul>

<hr>

<h2>Testing</h2>

<p>
Add JUnit or Spring Boot tests inside:
</p>

<pre>
src/test/java
</pre>

<p>
Write tests for controllers, services, and repository layers.
</p>

<hr>

<h2>Contributing</h2>

<p>
Fork the repository.  
Create a feature branch.  
Commit changes and open a Pull Request.
</p>

<hr>

<p align="center">
Created by <strong>Sumit Adeppa</strong>
</p>
