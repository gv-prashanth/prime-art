# Use the official maven/Java 11 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM openjdk:8-jdk-alpine AS build-env

# Set the working directory to /app
WORKDIR /app
# Copy the pom.xml file to download dependencies
COPY pom.xml ./
# Copy local code to the container image.
COPY src ./src

# Download dependencies and build a release artifact.
RUN mvn package -DskipTests

# Use OpenJDK for base image.
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:8-jdk-alpine

# Copy the jar to the production image from the builder stage.
COPY --from=build-env /app/target/prime-art*.jar /prime-art.jar

# Run the web service on container startup.
CMD ["java", "-jar", "/prime-art.jar"]
