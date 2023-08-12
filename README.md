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

## Spock Framework란?

- Groovy 언어를 이용하여 테스트 코드를 작성할 수 있는 프레임워크이며 JUnit과 비교하여 코드를 더 간결하게 작성 가능
- Groovy 언어는 동적 타입 프로그래밍 언어로 JVM 위에서 동작하며 Java 문법과 유사
- 테스트 메소드 이름을 문자열로 작성할 수 있으며 given, when, then 코드 블록을 명확히 구분

## Spock Framework 테스트 코드 작성 순서

- 테스트 클래스는 Groovy 클래스로 생성하고, Specification 클래스를 상속 받는다.
- feature(테스트 메서드는) def를 이용하여 함수로 선언하며, 하나 이상 블록이 존재해야 한다.
- given : 테스트에 필요한 값을 준비
- when : 테스트할 코드를 실행
- then : when과 함께 사용하며 예외 및 결과 값을 검증한다.
- expect : then과 같으며 when을 필요로 하지 않기 때문에 간단한 테스트 및 where와 같이 사용된다.
- where : 데이터가 다르고 로직이 동일한 경우 동잃나 테스트에 대한 중복 코드 제거 가능

## Spock 적용

build.gradle
- plugin : groovy
- dependencies 추가

test
- groovy directory 생성
- 이 곳에서 test 진행

## Test Containers 사용 이유

JPA 이용하여 CRUD 테스트 코드를 작성할 때 어떤 DB 환경이 좋을까?
- 운영환경과 유사한 스펙의 DB(개발환경DB) 사용하기
- 인메모리 DB(h2) 사용하기
- Docker 이용하기
- TestContainer를 이용하기
  - 운영환경과 유사한 DB스펙으로 독립적인 환경에서 테스트 코드를 작성하여 테스트가 가능하다.
  - Java 언어만으로 docker container를 활용한 테스트 환경 구성
  - 도커를 이용하여 테스트할 때 컨테이너를 직접 관리해야 하는 번거로움을 해결 해주며, 운영환경과 유사한 스펙으로 테스트 가능
  - 즉, 테스트 코드가 실행 될 때 자동으로 도커 컨테이너를 실행하여 테스트 하고,
    테스트가 끝나면 자동으로 컨테이너를 종료 및 정리
  - TestContainers는 다양한 모듈이 존재

## TestContainers 환경(MariaDB, Redis)

MariaDB와 Redis를 독립된 환경에서 테스트 코드 작성을 위해 TestContainers 적용
- build.gradle에 의존성 추가
- 테스트를 위한 test/resources/application.yml 파일 생성

## Spring Data JPA 사용시 주의사항

- JPA의 모든 데이터 변경은 아래와 같이 트랜잭션 안에서 실행된다!
- 즉, 트랜잭션 밖에서 데이터 변경은 Spring Data JPA 및 영속성 컨텍스트 반영되지 않는다.

## 영속성 컨텍스트(Persistence Context)

- 영속성 컨텍스트는 entity를 저장하고 관리하는 저장소이며, 어플리케이션과 데이터베이스 사이에 entity를 보관하는 가상의 데이터베이스 같은 역할
- Spring Data JPA에서 제공하는 save메소드 구현 코드를 보면 em.persist를 통해 영속성 컨텍스트에 저장
- 이때, entity는 영속상태
- 이미 영속성태인 경우 merge를 통해 덮어 쓴다.

## JPA Dirty Checking

- 코드에서 엔티티의 값만 변경했을 뿐인데, 데이터 베이스 업데이트 쿼리가 발생한다?
  - 이유는 Dirty Checking 덕분이며, Dirty란 상태의 변화가 생긴 정도를 의미한다.
- 즉, Dirty Checking이란 entity 상태 변경 검사
- JPA에서 트랜잭션이 끝나는 시점에 변화가 있는 모든 entity 객체를 데이터 베이스에 자동으로 반영해 준다.
- 내부 구조
  - JPA는 commit 하는 순간 내부적으로 flush가 호출되고, 이때 엔티티와 스냅샷을 비교
  - 1차 캐시에는 처음 들어온 상태인 엔티티 스냅샷을 넣어두고
    commit 하는 순간 변경된 값이 있는지 비교하여 변경된 값이 있으면 update 쿼리를 쓰기 지연 SQL에 넣어둔다.
- 주의 사항
  - 당연히 Dirty Checking은 영속성 컨텍스트가 관리하는 entity에만 적용된다.
  - 영속성 컨텍스트에 처음 저장된 순간 스냅샷을 저장해놓고,
    트랜잭션이 끝나는 시점에 비교하여 변경된 부분을 쿼리로 생성하여 데이터 베이스로 반영한다.
  - 즉, 영속 상태가 아닐 경우 값을 변경해도 데이터베이스에 반영되지 않는다.
  - 트랜잭션이 없이 데이터 변경 반영이 일어나지 않는다