## STEP2 질문 8개

- Project Structure & JPA
1. BookController, BookService, BookRepository의 역할과 요청 처리 흐름을 설명하세요.
Controller는 HTTP 요청을 받아 응답하고, Service는 비즈니스 로직을, Repository는 DB 접근을 맡는다. 
Controller --> Service --> Repository --> DB 순으로 내려갔다가, 결과가 거꾸로 올라와 JSON으로 응답된다.


관련 소스: BookController.get(), BookService.findById(), BookRepository.findById()

2. JpaRepository<Book, Long>만 선언해도 findAll(), save()를 사용할 수 있는 이유를 설명하세요.
JpaRepository가 CRUD 메서드를 이미 선언해 두었고, 실행할 때 Spring Data JPA가 구현체를 자동으로 만들어 주입해 줌 
<Book, Long>은 다룰 Entity와 ID의 타입이다.


관련 소스: BookRepository.java, BookService
- DTO & HTTP Response

3. 기존 API가 Book Entity를 직접 요청·응답에 사용할 때 발생할 수 있는 문제를 설명하세요.
   클라이언트가 id처럼 보내면 안 되는 값까지 보낼 수 있고, DB 구조가 그대로 노출될 수 있기 때문에 위험하다.
또 Entity를 바꾸면 API 모양도 같이 바뀔 수 있음


관련 소스: BookController.create(), update(), get()
4. Request DTO와 Response DTO를 분리하는 이유와 ResponseEntity가 필요한 상황을 설명하세요.
   Request DTO는 입력 받을 값만, Response DTO는 보여줄 값만 담아서 입력과 출력을 따로 관리할수 있다. 
Response Entity는 201, 404처럼 상태 코드를 직접 정해야 할 때 쓴다.


관련 소스: BookRequest, BookResponse.from(), BookController.create() & delete()

- Validation & Exception Handling

5. @Valid, @NotBlank, @Min은 각각 어느 시점에 어떤 동작을 수행하나요?
   @Valid는 JSON이 DTO로 바뀐 직후에 컨트롤러 메서드가 실행되기 전에 먼저 검증을 실행함. 
@NotBlank는 빈 문자열을 막고, @Min은 최솟값보다 작은 숫자를 막는다. 또한 여기서 검증에 실패하면 400 에러가 나게 된다.


관련 소스: BookRequest 필드, BookController.create()

6. @RestControllerAdvice와 @ExceptionHandler를 사용하면 예외 처리 흐름이 어떻게 바뀌나요?
   없으면 예외가 그대로 500 에러로 나가게 된다. 
있으면 모든 컨트롤러의 예외가 한 클래스로 모이고, 예외 종류별로 404나 400 같은 통일된 에러 응답을 만들어서 반환한다.


관련 소스: BookService.findById()의 orElseThrow(), 새로 만들 GlobalExceptionHandler

- Pagination & OpenAPI

7. Pageable과 Page<T>는 각각 어떤 역할을 하며 sort=price,desc는 어떻게 처리되나요?
   Pageable은 페이지 번호나 크기, 정렬 같은 요청 정보이고, Page는 데이터와 전체 개수랑 페이지 수를 담은 결과다. 
sort=price,desc는 ORDER BY price DESC로 바뀌어 실행할 수 있음.


관련 소스: BookController.list(), BookService.findAll(), findAll(Pageable)
8. /v3/api-docs와 /swagger-ui.html의 역할을 구분해서 설명하세요.
   - /v3/api-docs는 API 명세를 담은 JSON 원본
   - /swagger-ui.html은 그 JSON을 화면으로 보여주고 API를 직접 테스트해 볼 수 있는 UI


관련 소스: BookController 전체, build.gradle(springdoc 의존성 필요)