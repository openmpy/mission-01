# 스파르타 익명 게시판 서버 만들기

> Spring 주특기 첫번째 과제

## 🔧 구현 기능

- 게시글 작성 기능
    - `제목`, `작성자명`, `비밀번호`, `작성 내용`, `작성일`을 저장할 수 있습니다.
    - 저장된 게시글의 정보를 반환 받아 확인할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
- 선택한 게시글 조회 기능
    - 선택한 게시글의 정보를 조회할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
- 게시글 목록 조회 기능
    - 등록된 게시글 전체를 조회할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
    - 조회된 게시글 목록은 작성일 기준 내림차순으로 정렬 되어있습니다.
- 선택한 게시글 수정 기능
    - 선택한 게시글의 `제목`, `작성자명`, `작성 내용`을 수정할 수 있습니다.
        - 서버에 게시글 수정을 요청할 때 `비밀번호`를 함께 전달합니다.
        - 선택한 게시글의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 수정이 가능합니다.
    - 수정된 게시글의 정보를 반환 받아 확인할 수 있습니다.
        - 반환 받은 게시글의 정보에 `비밀번호`는 제외 되어있습니다.
- 선택한 게시글 삭제 기능
    - 선택한 게시글을 삭제할 수 있습니다.
        - 서버에 게시글 삭제를 요청할 때 `비밀번호`를 함께 전달합니다.
        - 선택한 게시글의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 삭제가 가능합니다.
- 예외 처리
    - 선택한 게시글 수정 및 삭제 요청 시 비밀번호가 일치하지 않을 경우 에러 정보를 반환합니다.

## 📚 스택

- JDK 17
- Spring Boot 3.1.8
- Spring Boot JPA
- Spring Boot Validation
- H2

## 📖 application-dev.yml

```yml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:test;MODE=MySQL
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
    show-sql: true
```

## 🖼️ Use Case Diagram

<img src="https://github.com/openmpy/mission-01/assets/150704638/2c271b9e-fed7-4c4c-8f31-1e48eadefa81" width=500 height=800>

## 🔖 ERD

<img src="https://github.com/openmpy/mission-01/assets/150704638/0b31f556-b34f-423e-b605-5073c1deb7b6" width=500 height=400>

## 📄 API 명세서

### 에러 처리

#### Example response

```http request
{
  "status": false,
  "message": "유효성 검사 실패",
  "data": {
    "contents": "내용을 입력해주세요.",
    "writer": "작성자를 입력해주세요.",
    "title": "제목을 입력해주세요."
  }
}
```

### 게시글 작성

`POST` 요청을 사용해서 게시글을 작성할 수 있습니다.

#### Request fields

| Path       | Type     | Description |
|------------|----------|-------------|
| `title`    | `String` | 제목          |
| `writer`   | `String` | 작성자         |
| `password` | `String` | 비밀번호        |
| `contents` | `String` | 내용          |

#### Example request

```http request
POST /api/v1/boards/
Content-Type: application/json

{
  "title": "제목",
  "writer": "홍길동",
  "password": "1234",
  "contents": "내용"
}
```

#### Response fields

| Path        | Type            | Description |
|-------------|-----------------|-------------|
| `title`     | `String`        | 제목          |
| `writer`    | `String`        | 작성자         |
| `contents`  | `String`        | 내용          |
| `createdAt` | `LocalDateTime` | 작성일자        |

#### Example response

```http request
{
  "status": true,
  "message": "게시글 작성",
  "data": {
    "title": "제목",
    "writer": "홍길동",
    "contents": "내용",
    "createdAt": "2024-02-20 13:51:34"
  }
}
```

### 게시글 조회

`GET` 요청을 사용해서 게시글을 조회할 수 있습니다.

#### Request fields

| Path | Type   | Description |
|------|--------|-------------|
| `id` | `Long` | 게시글 번호      |

#### Example request

```http request
GET /api/v1/boards/{id}
Content-Type: application/json
```

#### Response fields

| Path        | Type            | Description |
|-------------|-----------------|-------------|
| `id`        | `Long`          | 게시글 번호      |
| `title`     | `String`        | 제목          |
| `writer`    | `String`        | 작성자         |
| `contents`  | `String`        | 내용          |
| `createdAt` | `LocalDateTime` | 작성일자        |

#### Example response

```http request
{
  "status": true,
  "message": "게시글 조회",
  "data": {
    "id": 1,
    "title": "제목",
    "writer": "홍길동",
    "contents": "내용",
    "createdAt": "2024-02-20 13:51:34"
  }
}
```

### 게시글 목록 조회

`GET` 요청을 사용해서 게시글 목록을 조회할 수 있습니다.

#### Example request

```http request
GET /api/v1/boards/
Content-Type: application/json
```

#### Response fields

| Path        | Type            | Description |
|-------------|-----------------|-------------|
| `id`        | `Long`          | 게시글 번호      |
| `title`     | `String`        | 제목          |
| `writer`    | `String`        | 작성자         |
| `contents`  | `String`        | 내용          |
| `createdAt` | `LocalDateTime` | 작성일자        |

#### Example response

```http request
{
  "status": true,
  "message": "게시글 목록 조회",
  "data": [
    {
      "id": 2,
      "title": "제목2",
      "writer": "홍길동2",
      "contents": "내용2",
      "createdAt": "2024-02-20 13:59:42"
    },
    {
      "id": 1,
      "title": "제목",
      "writer": "홍길동",
      "contents": "내용",
      "createdAt": "2024-02-20 13:51:34"
    }
  ]
}
```

### 게시글 수정

`PUT` 요청을 사용해서 게시글을 수정할 수 있습니다.

#### Request fields

| Path       | Type     | Description |
|------------|----------|-------------|
| `title`    | `String` | 제목          |
| `writer`   | `String` | 작성자         |
| `password` | `String` | 비밀번호        |
| `contents` | `String` | 내용          |

#### Example request

```http request
PUT /api/v1/boards/{id}
Content-Type: application/json

{
  "title": "제목",
  "writer": "홍길동",
  "password": "1234",
  "contents": "내용"
}
```

#### Response fields

| Path        | Type            | Description |
|-------------|-----------------|-------------|
| `id`        | `Long`          | 게시글 번호      |
| `title`     | `String`        | 제목          |
| `writer`    | `String`        | 작성자         |
| `contents`  | `String`        | 내용          |
| `createdAt` | `LocalDateTime` | 작성일자        |

#### Example response

```http request
{
  "status": true,
  "message": "게시글 수정",
  "data": {
    "id": 1,
    "title": "제목",
    "writer": "홍길동",
    "contents": "내용",
    "createdAt": "2024-02-20 13:51:34"
  }
}
```

### 게시글 삭제

`DELETE` 요청을 사용해서 게시글을 삭제할 수 있습니다.

#### Request fields

| Path       | Type     | Description |
|------------|----------|-------------|
| `id`       | `Long`   | 게시글 번호      |
| `password` | `String` | 비밀번호        |

#### Example request

```http request
DELETE /api/v1/boards/{id}?password={password}
Content-Type: application/json
```

#### Response fields

| Path | Type   | Description |
|------|--------|-------------|
| `id` | `Long` | 게시글 번호      |

#### Example response

```http request
{
  "status": true,
  "message": "게시글 삭제",
  "data": 1
}
```
