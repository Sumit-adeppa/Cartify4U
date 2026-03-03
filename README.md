<h1 align="center">Cartify4U</h1>

<p align="center">Spring Boot based E-Commerce Backend API for Cartify4U with MongoDB, JWT-Auth and Razorpay integration.</p>

<hr>

<h2>Description</h2>

<p>
Cartify4U is a backend application built with Spring Boot.  
The API supports user authentication with JWT, MongoDB database operations, and Razorpay payment integration.  
It provides endpoints for cart management, products, users, orders and payments.
</p>

<hr>

<h2>Features</h2>

<ul>
  <li>Spring Boot REST API</li>
  <li>JWT Authentication and Authorization</li>
  <li>MongoDB NoSQL Database Support</li>
  <li>Cart CRUD operations</li>
  <li>Razorpay Payment Gateway Integration</li>
  <li>Validation for request data</li>
  <li>Docker support (Dockerfile and docker-compose.yml included)</li>
</ul>

<hr>

<h2>Tech Stack</h2>

<ul>
  <li>Java 17</li>
  <li>Spring Boot 3.5.10</li>
  <li>Spring Web</li>
  <li>Spring Data MongoDB</li>
  <li>Spring Security Crypto</li>
  <li>JWT (JSON Web Tokens)</li>
  <li>MongoDB</li>
  <li>Razorpay Java SDK</li>
  <li>Maven Build Tool</li>
  <li>Docker &amp; Docker Compose</li>
</ul>

<hr>

<h2>Prerequisites</h2>

<ul>
  <li>Java JDK 17+</li>
  <li>Apache Maven</li>
  <li>MongoDB instance (local or cloud)</li>
  <li>Razorpay account Key &amp; Secret</li>
  <li>Docker (optional, for containerization)</li>
</ul>

<hr>

<h2>Application.properties</h2>

<p>
This project does not include the <code>application.properties</code> file.  
You must create it manually under <code>src/main/resources</code>.  
Include the following keys and values:
</p>

<pre>
spring.data.mongodb.uri = &lt;your_mongodb_connection_string&gt;
jwt.secret = &lt;your_jwt_secret&gt;
razorpay.key = &lt;your_razorpay_key&gt;
razorpay.secret = &lt;your_razorpay_secret&gt;
server.port = 8080
</pre>

<hr>

<h2>Getting Started</h2>

<h3>Clone the Project</h3>

<pre>
git clone https://github.com/Sumit-adeppa/Cartify4U.git
cd Cartify4U
</pre>

<h3>Build the Application</h3>

<pre>
mvn clean install
</pre>

<h3>Run with Maven</h3>

<pre>
mvn spring-boot:run
</pre>

<h3>Run with Jar</h3>

<pre>
java -jar target/cartify-0.0.1-SNAPSHOT.jar
</pre>

<hr>

<h2>Docker Setup (Optional)</h2>

<p>You can use Docker to containerize the application and MongoDB.</p>

<h3>Build Docker Image</h3>

<pre>
docker build -t cartify4u .
</pre>

<h3>Run with Docker Compose</h3>

<pre>
docker-compose up -d
</pre>

<hr>

<h2>API Overview</h2>

<p>Examples of endpoints (adjust paths as needed):</p>

<ul>
  <li><strong>POST</strong> /auth/register</li>
  <li><strong>POST</strong> /auth/login</li>
  <li><strong>GET</strong> /products</li>
  <li><strong>POST</strong> /cart</li>
  <li><strong>POST</strong> /orders</li>
  <li><strong>POST</strong> /payment/razorpay</li>
</ul>

<hr>

<h2>Environment Variables</h2>

<p>Store sensitive keys as environment variables or use secure config.</p>

<ul>
  <li>MONGO_URI</li>
  <li>JWT_SECRET</li>
  <li>RAZORPAY_KEY</li>
  <li>RAZORPAY_SECRET</li>
</ul>

<hr>

<h2>Testing</h2>

<p>
You can write JUnit and Spring Boot Test cases in <code>src/test/java</code>.  
Add tests as required for controllers and services.
</p>

<hr>

<h2>Contributing</h2>

<p>
Contributions are welcome.  
Please fork the repository, make changes, and open a Pull Request.
</p>

<p align="center">Created by <strong>Sumit-adeppa</strong></p>
