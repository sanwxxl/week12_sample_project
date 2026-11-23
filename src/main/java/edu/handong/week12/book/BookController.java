package edu.handong.week12.book;

import edu.handong.week12.dto.BookRequest;
import edu.handong.week12.dto.BookResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Book", description = "책 CRUD API")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;


    public BookController(BookService service) {
        this.service = service;
    }

    @Operation(summary = "책 목록 조회 (페이징, 정렬)")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> list(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "책 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @Operation(summary = "책 등록")
    // POST 요청이 들어오면 이 메서드가 실행
    @PostMapping
    // @RequestBody: 요청 JSON을 BookRequest DTO로 변환한다
    // 아래 코드애서 @Valid는 메서드 실행 전에 BookRequest의 검증 규칙(@NotBlank, @Min 등등)을 검사하고, 실패하면 400 응답으로 넘어간다
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        // 검증을 통과한 DTO를 Service에 넘겨 저장하고, 저장된 결과를 BookResponse DTO로 받는다
        BookResponse response = service.create(request);
        // ResponseEntity로 상태 코드 201 Created와 응답 DTO를 함께 반환한다(여기서 넘기는건 HttpStatus로 넘긴디)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "책 수정")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "책 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
