# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

# Step3 TODO list
- [ ] 인수 조건 도출
- [ ] 인수 테스트 작성
- [ ] 권한이 없을 떄 권한이 필요한 API 호출 시 401 코드 응답
  - [ ] 즐겨 찾기 기능 구현
    - [ ] 정상적인 시작, 도착 지점을 입력하면 즐겨찾기 생성
    - [ ] 비정상적인 시작, 도착 지점을 입력하면 예외를 발생
    - [ ] 즐겨찾기 조회 기능
    - [ ] 정상적인 즐겨찾기 번호를 입력하면 삭제 가능
    - [ ] 비정상적인 즐겨찾기 번호를 입력하면 예외를 발생
  - [ ] 내 정보 관리

# Step2 PR 수정 TODO list
- [x] Exception 메시지들 상수화
- [x] AuthenticationChainingFilter#afterAuthentication 에서 assert 로 UserDetail 인자 검증하기
- [x] BasicAuthenticationFilter ":" 상수로 추출
- [x] 전체적으로 개행을 통하 가독성 높히기
- [x] UserDetailsService에 @FunctionalInterface 추가하기
- [x] MemberService CQRS에 따라 분리해보기

# Step2 TODO list
- [x] 인증 로직 리팩터링
  - [x] XXXAuthenticationFilter의 구조화
    - [x] TokenAuthenticationInterceptor와 UsernamePasswordAuthenticationFilter 추상화
    - [x] BasicAuthenticationFilter와 BearerTokenAuthenticationFilter 추상화
  - [x] auth 패키지와 member 패키지에 대한 의존 제거
    - [x] UserDetailsService를 추상화 하여 auth -> member 의존을 제거하기

# Step1 PR 수정 TODO list
- [x] Filter 에서 Exception을 잡을때 세부적으로 예외 catch하도록 변경
- [x] 상수 추출하기
- [x] ObjectMapper를 DI받도록 변경하기
- [x] Token Interceptor 의 authenticate 에서 반환부분 수정
- [x] 로그인 하지 않은경우 지하철 노선 확인하기
- [x] 로그인 하지 않은경우 구간 관리 확인하기
- [x] Member 관리 인수 테스트 작성

# Step1 TODO list
- [x] 지하철역, 노선, 구간을 변경하는 API는 관리자만 접근이 가능하도록 수정
- [x] Form 기반 로그인과 Bearer 기반 로그인 기능을 구현
  - [x] UsernamePasswordAuthenticationFilter 구현
  - [x] BearerTokenAuthenticationFilter 구현
- [x] AuthAcceptanceTest 테스트를 통해 기능 구현을 확인하기
- [x] 인수 테스트를 수행하기 전 공통으로 필요한 멤버, 역할은 초기에 설정하기 - DataLoader 활용
- [x] API 별 권한 검증을 위해 @Secured 를 사용하기
