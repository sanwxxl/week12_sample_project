package edu.handong.week12.book;

import edu.handong.week12.dto.BookRequest;
import edu.handong.week12.dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  // pageable에는 컨트롤러에서 받은 page, size, sort(ex: price,desc) 정보가 들어 있다
  public Page<BookResponse> findAll(Pageable pageable) {
    // Repository가 pageable 정보로 해당 페이지의 Book만 정렬해서 조회하며, Page<Book>으로 반환한다
    // Page 안의 Book을 하나씩 BookResponse DTO로 바꾼다.
    return repository.findAll(pageable).map(BookResponse::from);
  }// TODO STEP 6: Pagination/Sorting

  public BookResponse findById(Long id) {
    // Repository로 DB에서 id에 해당하는 책을 찾는다. 결과는 있을 수도 없을 수도 있기 때문에, Optional로 받음
    // Optional이 비어 있으면(= 없는 id면) BookNotFoundEcxeption을 던지고, GlobalExceptionHandler가 404로 응답한다
    Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

    // 찾은 Book Entity를 BookResponse DTO로 바꿔서 반환
    return BookResponse.from(book);

  } // TODO STEP 5: 404

  public BookResponse create(BookRequest request) {
    Book book = new Book(request.title(), request.author(), request.price());
    Book saved = repository.save(book);
    return BookResponse.from(saved);
  }// TODO STEP 3: DTO

  public BookResponse update(Long id, BookRequest request) {
    Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    book.update(request.title(), request.author(), request.price());
    Book saved = repository.save(book);
    return BookResponse.from(saved);
  }

  public void delete(Long id) {
    repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    repository.deleteById(id);
  } // TODO: 404 if absent
}
