# pharmacy-recommendation

## Dependencies

- JDK 11
- Spring boot 2.6.7
- Gradle
- Lombok
- Spring Configuration Processor
- Spring Web
- Spring Data JPA
- MariaDB Driver

## Plugin

- Spock Framework Enhancements
- Handlebars/Mustache
- Lombok
  - Preference/Annotation Processor : Enable annotation processing Check!
- 설정값 변경
  - Build/Gradle
    - Build and run using & Run tests using : IntelliJ IDEA  

## Docker 맛보기

- 원하는 jar 파일명으로 변경(app.jar)
  - build.gradle
    ```js
    bootJar {
        archiveFileName = 'app.jar'
    }
    ```
- gradle wrapper를 이용하여 jar 파일(App 실행) 생성
  ```bash
  ./gradlew build
  ```
- build/libs/jar파일(모든 의존성 포함) 생성된 것 확인
- Dockerfile(build) -> Docker Image(run) -> Docker Container
  - Dockerfile 생성
    ```js
    FROM openjdk:11
    ARG JAR_FILE=build/libs/app.jar
    COPY ${JAR_FILE} ./app.jar
    ENV TZ=Aisa/Seoul
    ENTRYPOINT ["java", "-jar", "./app.jar"]
    ```
  - Docker Image(run)
    ```bash
    docker build -t junesworld/application-pharmacy-recommendation .
    docker images
    ```
  - Docker Container 생성 후 실행
    ```bash  
    docker run junesworld/application-pharmacy-recommendation -p 8080:8080
    ```
      
## Docker Settings

Folder
- database
  - config/mariadb.cnf : 한글 깨짐 현상 방지
- redis


- docker-compose-local.yml 파일 생성
- .env 파일 생성 후 .gitignore 추가  
- docker 실행
  ```bash
  docker-compose -f docker-compose-local.yml up 
  ``` 
- container 정상 작동 확인(새로운 터미널)
  ```bash
  docker ps
  ``` 
  
## Springboot Database Redis Connetion

application.yml 파일에서 설정 후 Intellij에서 환경변수 설정
- Edit/environment/modify options/environment variables