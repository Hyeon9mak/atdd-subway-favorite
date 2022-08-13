# 인수 테스트와 인증

## 인증 기반 인수 테스트

## 기능 요구사항

- Form 기반 로그인과 Bearer 기반 로그인 기능 구현
    - `UsernamePasswordAuthenticationFilter`  `BearerTokenAuthenticationFilter` `AuthAcceptanceTest`
- 지하철역, 노선, 구간을 변경하는 API는 관리자만 가능하도록 수정
    - Token을 이용한 로그인을 통해 관리자 여부를 판단
    - Token 값은 Header에서 추출
    - 생성, 수정, 삭제 API
    - 그외는 모든 사용자가 접근 가능하도록 (조회 API)

## 요구사항 설명

### 관리자 전용 API 접근 제한

- 관리자 전용 API에 대해 접근 기능을 구현하려면 관리자 멤버와 역할이 미리 설정되어있어야 함
- 인수 테스트를 수행하기 전 공통으로 필요한 멤버, 역할은 초기에 설정할 수 있도록
- DataLoader를 활용 해보자

### 권한 검증

- API 별 권한 검증을 위해 @Secured 활용
- 관리자만 접근 가능한 기능을 검증하는 인수 테스트 만들기
- 관리자 여부는 Header에 담기는 Token 정보를 이용
- 토큰이 유효하지 않은 경우 에러 응답이 오는지 확인'

----

## 인증 기반 인수 테스트 리팩터링

## 프로그래밍 요구사항

- 인증 로직(auth 패키지)에 대한 리팩터링을 진행

## 요구사항 설명

### 리팩터링 포인트

- `AuthenticationFilter` 구조화
  - 인증 성공 후 Interceptor chain을 진행하지 않고 응답하는 필터와
  - 인증 성공 후 다음 Intercaptor chain을 수행하는 필터를 구조화
- 이 차이를 참고하여 각각 추상화가 가능하다

### Auth 패키지와 Member 패키지에 대한 의존성 제거

- 현재는 auth 패키지와 member 패키지가 서로 의존하고 있음
- UserDetailsService를 추상화 하여 auth → member 의존을 제거하기

### 주의사항 !

- 기존 테스트 코드를 먼저 제거하고 리팩토링을 수행할 경우 엄청난 재앙이 시작될 수 있음!
- 새로운 테스트를 먼저 작성하고 테스트를 만족하는 프로덕션 코드를 만들자
- 새로운 테스트가 기존 코드를 모두 대체했다면 그때 기존 테스트와 프로덕션 코드를 삭제하기
  - 단, 이 상태에선 코드 중복이 되어있는 상태를 짧게 가져가도록 해야함

--- 
# 인수 테스트와 인증 - 즐겨찾기 기능 구현

## 기능 요구사항

- 요구사항 설명에서 제공되는 추가된 요구사항을 기반으로 즐겨 찾기 기능을 리팩터링하라
- 추가된 요구사항을 정의한 인수 조건 도출
- 인수 조건을 검증하는 인수 테스트 작성하기
- 예외 케이스에 대한 검증도 포함하기

## 프로그래밍 요구사항

- 인수 테스트 주도 개발 프로세스에 맞춰서 기능을 구현
  - 요구사항 설명을 참고하여 인수 조건을 정의
  - 인수 조건을 검증하는 인수 테스트 작성
  - 인수 테스트를 충족하는 기능 구현
- 인수 조건은 인수 테스트 메서드 상단에 주석으로 작성
- 인수 테스트 이후 기능 구현은 TDD로 진행하기
  - 도메인 레이어 테스트는 필수 !!

---

## 요구사항 설명

- 생성 API  **{ POST /favorites }**
  - 로그인 필요
  - statusCode 201,  Location Header
- 조회 API **{ GET /favorites}**
  - 로그인 필요
  - statusCode 200
- 삭제 API **{ DELETE /favorites/{id} }**
  - 로그인 필요
  - statusCode 204

---

## TODO LIST

### 생성 API

- 인수 조건 도출
- 인수 테스트 작성
- 단위 테스트 작성
  - 즐겨찾기 등록 단위 테스트
  - 예외 케이스 테스트 작성
- 예외 케이스
  - 유저가 등록되지 않은 경우
  - 즐겨찾기할 역이 존재하지 않는 경우
  - 즐겨찾기할 경로의 역이 서로 이어져있지 않은 경우
  - 출발지와 도착지가 동일한 경우
  - 출발지나 도착지가 비어있는 경우
  - 이미 등록된 즐겨찾기인 경우


### 조회 API

- 인수 조건 도출
- 인수 테스트 작성
- 단위 테스트 작성
  - 즐겨찾기 조회 단위 테스트
  - 예외 케이스 테스트 작성
- 예외 케이스
  - 유저가 등록되지 않은 경우
  - 즐겨찾기가 존재하지 않는 경우

### 삭제 API

- 인수 조건 도출
- 인수 테스트 작성
- 단위 테스트 작성
  - 즐겨찾기 삭제 단위 테스트
  - 예외 케이스 테스트 작성
- 예외 케이스
  - 유저가 등록되지 않은 경우
  - 즐겨찾기가 존재하지 않는 경우
