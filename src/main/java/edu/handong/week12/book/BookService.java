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

  public Page<BookResponse> findAll(Pageable pageable) {
    return repository.findAll(pageable)
            .map(BookResponse::from);
  }// TODO STEP 6: Pagination/Sorting

  public BookResponse findById(Long id) {
    Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
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
