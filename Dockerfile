# Step 1: Use a base image containing Java runtime
FROM openjdk:21-jdk

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Add the jar file built by Spring Boot to the container
COPY target/simpleWebApp-0.0.1-SNAPSHOT.jar /app/app.jar

# Step 4: Expose the port your application runs on
EXPOSE 8086

# Step 5: Set the command to run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
