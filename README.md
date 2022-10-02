# Ostock Licensing Service

## Introduction

1.  A Spring Cloud Config server that is deployed as Docker container and can manage a services configuration information using a file system/classpath or GitHub-based repository.
2.  A licensing service that will manage licensing data used within Ostock.
3.  A Postgres SQL database used to hold the data.


# The build command

Will execute the [Spotify dockerfile plugin](https://github.com/spotify/dockerfile-maven) defined in the pom.xml file.

Running the above command at the root of the project directory will build all the projects. If everything builds successfully you should see a message indicating that the build was successful.

# The Run command

This command will run our services using the docker-compose.yml file located in the /docker directory.

If everything starts correctly you should see a bunch of Spring Boot information fly by on standard out.  At this point all the services needed for the chapter code examples will be running.

# Database
You can find the database script as well in the docker directory.

```bash
# To build service:
$ ./mvnw clean package

# To build service and create docker image:
$ ./mvnw clean package dockerfile:build
# of
$ ./mvnw spring-boot:build-image

# To start the docker image, stay in the root directory and  Run the following command: 
$ docker-compose -f docker/docker-compose.yml up
```

# Eureka
Browsers to http://localhost:8070 to view the Eureka dashboard

# Docker commands
```
docker images
docker run ostock/licensing-service:0.0.1-SNAPSHOT
docker run -d ostock/licensing-service:0.0.1-SNAPSHOT
docker run -it -p8080:8080 ostock/licensing-service:0.0.1-SNAPSHOT
docker stop <container_id>
```

# Docker Compose commands
```bash
# Builds the images for your application and starts the services you define.
# This command downloads all the necessary images and then deploys these and starts the container.
# The -d parameter indicates to run Docker in the background.
docker-compose up -d

# Lets you view all the information about your latest deployment.
docker-compose logs

# Lets you view the logs for a specific service.
# To view the licensing service deployment, for example, use this command: docker-compose logs licenseService.
docker-compose logs <service_id>

# Outputs the list of all the containers you have deployed in your system.
docker-compose ps

# Stops your services once you have finished with them. This also stops the containers.
docker-compose stop

# Shuts down everything and removes all containers.
docker-compose down
```

# Troubleshooting

```
# An attempt was made to access a socket in a way forbidden by its access permissions
net stop winnat
net start winnat
```
