<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"><img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"><img src="https://img.shields.io/badge/PostgreSQL-007396?style=for-the-badge&logo=PostgreSQL&logoColor=white">



# Notice API

공지사항 API


## 목차

1. [소개](#소개)
2. [개발자소개](#개발자소개)
3. [개발환경](#개발환경)
4. [시작하기](#시작하기)
    - [설치](#설치)
    - [사용법](#사용법)


## 소개

공지사항 등록 및 조회 등, 공지사항 관련한 작업을 위한 API 입니다.


## 개발자소개

- **이름** : 최금영
- **직무** : 백엔드
- **주요언어** : JAVA


## 개발환경

- **Version** : JAVA 8
- **Framwork** : Spring Boot 2.7.5
- **IDE** : IntelliJ
- **DATABASE** : PostgreSQL
- **ORM** : JPA(Hibernate)
- **SPEC API** : Swagger 3


## 설치

**Requirment** </br>
Java 8 이상 </br>
Postgresql 12 이상


```
$ git clone https://github.com/geumyeongchoi/rsupport-noti.git
```


## 시작하기

1) Postgresql 설치
2) notice\ref\db 에 있는 init.sql로 필요한 스키마 생성
3) 아래 명령어 수행

```
$ cd rsupport-noti
$ gradlew build
$ cd build/libs
$ java -jar notice-0.0.1-SNAPSHOT.jar
```



## 사용법

따로 port 를 지정하지 않아 8080을 사용합니다. 

swagger-ui에서 테스트 가능합니다.

**접속URL**: http://localhost:8080/swagger-ui/index.html

![image](https://github.com/geumyeongchoi/rsupport-noti/assets/96420263/03783833-e80e-4c3a-92a6-80b3b1f39fe9)


