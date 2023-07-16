# University Entry Service


1. **Clone the Repository:**

2. **Install Dependencies using Maven:**

   - Make sure you have Maven installed and configured in your system.
   - In the IntelliJ IDEA terminal or command line, navigate to the project root directory.
   - Run the following command to install the project dependencies:
   
     ```shell
     mvn install
     ```

3. **Ensure PostgresSQL is Running:**

   - Make sure you have PostgresSQL installed and running on your machine.
   - If not, download and install PostgresSQL from the official website.
   - Start the PostgresSQL service before proceeding to the next steps.

4. **Configure Application Properties:**

   - Locate the `src/main/resources/application.example.yml` file in the project.
   - Make a copy of this file and rename it to `application.yml`.
   - Open the `application.yml` file and set the necessary configurations.
   - Update the DB username, password, and database name to match your PostgresSQL setup.



## Deployment

### Docker

This application can be built and run using Docker. To do so, you need to have Docker installed on your machine.

#### Building the Docker image

Run the following command to build the Docker image. Replace `my-spring-app` with the desired image name.

```shell
docker build -t entry-service .
```
#### Running the Docker container
You can start the application using the following command:

```shell
docker run -p 8080:8080 \
-e SPRING_DATASOURCE_URL=jdbc:postgresql://[host]:[port]/[db] \
-e SPRING_DATASOURCE_USERNAME=[username] \
-e SPRING_DATASOURCE_PASSWORD=[password] \
-e SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver \
-e JWT_SECRET=[jwt-secret] \
-e JWT_EXPIRATION=[expire-time] \
entry-service
```

