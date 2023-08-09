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

## 요구사항 분석

1. 주소를 문자 데이터로 입력하여 요청
2. 주소 정보를 위도, 경도 변환 요청
3. 주소를 위도, 경도 반환
4. 약국 현황 데이터 Redis 조회
5. Redis 장애인 경우 DB 조회
6. 가장 가까운 약국 3개 추출
7. 길안내 URL 저장(약국 길안내 정보)
8. 추천 약국 길안내 제공
      
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

## 개발 전 API 호출

KAKAO 내 Application [https://developers.kakao.com]


Postman
- Get
  - https://dapi.kakao.com/v2/local/search/address.json?query=[약국 주소]
- Headers
  - Authorization
  - KakaoAK [Rest API]

## KAKAO 주소검색 API 구현하기

application.yml
```agsl
kakao:
  rest:
    api:
      key: ${KAKAO_REST_API_KEY}
```
- edit/환경변수 설정
- api/dto, service 패키지 생성