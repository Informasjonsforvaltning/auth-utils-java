# Auth utils java

## Overview
Module for mocking authentication 

## Requirements 
- Java 11
- Maven

## Running
The module can be run either as a  jar running on localhost, in a docker container from local image, or a docker container from an 
image hosted on [Informasjonsforvaltnings package repository](https://github.com/orgs/Informasjonsforvaltning/packages)  

### Setup
In project root:<br>
Run `mvn clean install` to build the project 

1. Running from commandline:<br> 
`java -jar ./target/auth-utils-java-1.0-SNAPSHOT-jar-with-dependencies.jar`      

2. Running in docker container <br>
`docker container run -p 8084:8084 --name auth auth-utils-java`

####Add module as jwk store in spring
In application-dev.properties file add line:<br>
`security.oauth2.resource.jwk.key-set-uri: http://localhost:8084/jwk`
set env variable `SPRING_ACTIVE_PROFILE￿` to `dev` on startup
#### Get jwt
`curl http://localhost:8084/jwt/read`<br>
`curl http://localhost:8084/jwt/write`<br>
`curl http://localhost:8084/jwt/admin`<br>


### Customization

#### Change port
*Default: 8084* <br> 

In commandline<br>
`java -jar -DPORT=8201 ./target/auth-utils-java-1.0-SNAPSHOT-jar-with-dependencies.jar`

In docker: change hostport mapping <br>
`docker container run -p 8021:8084 --name auth auth-utils-java`￿<br><br>

#### Change access string type
*format: `[type]:[orgnumber]:[rights]`*<br>
*default:  `organisation:910244132:[rights]`* <br>

In commandline:
`java -jar -DTYPE=different ./target/auth-utils-java-1.0-SNAPSHOT-jar-with-dependencies.jar`<br>

In docker you change type by setting the env variable `TYPE`<br>
`docker container run -p 8084:8084 -e TYPE='different' --name auth auth-utils-java`

### Audience
#### Updating audience temporarily
In commandline seoerated by comma:
`java -jar -DAUD=other-audience,yet-another-audience ./target/auth-utils-java-1.0-SNAPSHOT-jar-with-dependencies.jar`<br>

In docker you change type by setting the env variable `AUD`<br>
`docker container run -p 8084:8084 -e AUD=other-audience,yet-another-audience --name auth auth-utils-java`



#### Updating audience permanently
Update `val audience` in [JwtToken.java](src/main/kotlin/no/brreg/informasjonsforvaltning/jwk/JwtToken.kt) 
and rebuild image

See [FAQ](./FAQ.md) for more on audience and access string
