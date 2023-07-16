# Start with a base image containing Java runtime (JDK 17)
FROM openjdk:17-jdk-slim as build

WORKDIR /workspace/app

# Add Maven dependencies (to improve caching - optional)
ADD pom.xml ./pom.xml

# Install maven
RUN apt-get update && \
    apt-get install -y maven

# Package application code
COPY src ./src

# Compile the application
RUN mvn clean package -DskipTests

# The final image
FROM openjdk:17-jdk-slim

VOLUME /tmp

# Add the application's jar to the container
COPY --from=build /workspace/app/target/UE-0.0.1-SNAPSHOT.jar UE.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/UE.jar"]
