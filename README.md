# Auth utils java

## Overview
Module for mocking authentication 

## Requirements 
- Java 11
- Maven

## Running
The module can be run either as a jar running on localhost, in a docker container from local image, or a docker container from an 
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

- In terminal<br>
`java -jar -custom.port=8201 ./target/auth-utils-java-1.0-SNAPSHOT-jar-with-dependencies.jar`

- In docker: change hostport mapping <br>
`docker container run -p 8021:8084 --name auth auth-utils-java`￿<br><br>

#### Change access string type
*format: `[type]:[orgnumber]:[rights]`*<br>
*default:  `organisation:910244132:[rights]`* <br>

 - In terminal:<br>
`java -jar -Dcustom.type=different -Dcustom.org=91919191 ./target/auth-utils-java-1.0-SNAPSHOT-jar-with-dependencies.jar`<br>

 - In docker you change type by setting env variables `type`, `port` and `org` <br>
`docker container run -p 8084:8084 -e type='different' --name auth auth-utils-java`

-  In docker with .env file
`container run -p 8084:8084 --env-file ./env.list --name auth auth-utils-java`<br>
see [env.list](./env.list) for an example.

#####Change in request

Append pararmeters `type` and/or `org` to your jwt request<br>
```curl localhost:8084/jwt/read?org=6786876``` 
```curl localhost:8084/jwt/write?org=6786876&type=special``` 
```curl localhost:8084/jwt/write?type=special``` 


### Audience
#### Updating audience temporarily
- In terminal seperated by comma:<br>
`java -jar -Dcustom.aud=other-audience,yet-another-audience ./target/auth-utils-java-1.0-SNAPSHOT-jar-with-dependencies.jar`<br>

- In docker you change type by setting the env variable `aud`<br>
`docker container run -p 8084:8084 -e aud=other-audience,yet-another-audience --name auth auth-utils-java`


#### Updating audience permanently
Update `val audience` in [JwtToken.java](src/main/kotlin/no/brreg/informasjonsforvaltning/jwk/JwtToken.kt) 
and rebuild image

See [FAQ](./FAQ.md) for more on audience and access string
