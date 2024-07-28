package com.challenge.book.catalog;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.book.catalog.dto.BookRequestDTO;
import com.challenge.book.catalog.exception.RecordNotFoundException;
import com.challenge.book.catalog.model.Author;
import com.challenge.book.catalog.model.Book;
import com.challenge.book.catalog.model.Subject;
import com.challenge.book.catalog.repository.BookRepository;
import com.challenge.book.catalog.service.AuthorService;
import com.challenge.book.catalog.service.BookService;
import com.challenge.book.catalog.service.SubjectService;
import com.challenge.book.catalog.service.impl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

        private BookService bookService;
        private SubjectService subjectService;
        private AuthorService authorService;
        private BookRepository repository;
        private List<Author> authores;
        private List<Subject> subjects;

        @BeforeEach
        void beforeCreatePageRequest() {
                this.repository = Mockito.spy(BookRepository.class);
                this.bookService = new BookServiceImpl(this.repository, this.authorService, this.subjectService);
                authores = new ArrayList<Author>();
                subjects = new ArrayList<Subject>();
                authores.add(Author.builder()
                                .codAu("123")
                                .name("Freitas")
                                .build());

                subjects.add(Subject.builder()
                                .codAs("123")
                                .description("Romance")
                                .build());
        }

        @Test
        void testShouldCreateOneBook() {
                // Arrange
                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();
                BookRequestDTO dto = BookRequestDTO.builder()
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();

                when(this.repository.save(any())).thenReturn(book);

                // Action
                Book bookCreated = this.bookService.create(dto);

                // Asserts
                verify(this.repository, times(1)).save(any(Book.class));
                Assertions.assertThat(bookCreated).isNotNull();
                Assertions.assertThat(bookCreated.getTitle()).isEqualTo(book.getTitle());
        }

        @Test
        void testShouldUpdateOneBook() {
                // Arrange
                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game 2")
                                .releaseYear("2025")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();
                BookRequestDTO dto = BookRequestDTO.builder()
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game 2")
                                .releaseYear("2025")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();

                when(this.repository.save(any())).thenReturn(book);
                when(this.repository.findById("123")).thenReturn(Optional.of(
                                book));

                // Action
                Book bookUpdated = this.bookService.update("123", dto);

                // Asserts
                verify(this.repository, times(1)).findById("123");
                verify(this.repository, times(1)).save(any(Book.class));
                Assertions.assertThat(bookUpdated).isNotNull();
                Assertions.assertThat(bookUpdated.getTitle()).isEqualTo(book.getTitle());

        }

        @Test
        void testShouldUpdateWithRecordNotFoundException() {
                // Arrange
                BookRequestDTO dto = BookRequestDTO.builder()
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();

                // Action
                Exception exception = assertThrows(RecordNotFoundException.class, () -> {
                        this.bookService.update("123", dto);
                });
                // Asserts
                assertTrue(exception instanceof RecordNotFoundException);
        }

        @Test
        void testShouldDeleteOneBook() {
                // Arrange
                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();
                BookRequestDTO dto = BookRequestDTO.builder()
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();

                when(this.repository.save(any())).thenReturn(book);
                when(this.repository.findById("123")).thenReturn(Optional.of(
                                book));

                // Action
                Book bookCreated = this.bookService.create(dto);
                this.bookService.delete(bookCreated.getCodL());

                // Asserts
                verify(this.repository, times(1)).findById("123");
                verify(this.repository, times(1)).deleteById("123");
        }

        @Test
        void testShouldDeleteWithRecordNotFoundException() {
                // Arrange
                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();

                // Action
                Exception exception = assertThrows(RecordNotFoundException.class, () -> {
                        this.bookService.delete(book.getCodL());
                });
                // Asserts
                assertTrue(exception instanceof RecordNotFoundException);
        }

        @Test
        void testShouldGetListOfAllBooks() {
                // Arrange
                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(Collections.emptyList())
                                .priceByChannels(Collections.emptyList())
                                .subjects(Collections.emptyList())
                                .build();

                List<Book> booksMock = new ArrayList<Book>();
                booksMock.add(book);
                when(this.repository.findAll()).thenReturn(booksMock);

                // Action
                List<Book> books = this.bookService.getAll();

                // Asserts
                verify(this.repository, times(1)).findAll();
                Assertions.assertThat(books.size()).isEqualTo(1);
        }

        @Test
        void testShouldExistAuthor() {

                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(authores)
                                .subjects(subjects)
                                .priceByChannels(Collections.emptyList())
                                .build();

                List<Book> booksMock = new ArrayList<Book>();
                booksMock.add(book);
                when(this.repository.findAll()).thenReturn(booksMock);

                // Action
                boolean hasAuthor = this.bookService.existAuthor("123");

                // Asserts
                verify(this.repository, times(1)).findAll();
                Assertions.assertThat(hasAuthor).isEqualTo(true);
        }

        @Test
        void testShouldExistSubject() {

                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(authores)
                                .subjects(subjects)
                                .priceByChannels(Collections.emptyList())
                                .build();

                List<Book> booksMock = new ArrayList<Book>();
                booksMock.add(book);
                when(this.repository.findAll()).thenReturn(booksMock);

                // Action
                boolean hasSubject = this.bookService.existSubject("123");

                // Asserts
                verify(this.repository, times(1)).findAll();
                Assertions.assertThat(hasSubject).isEqualTo(true);
        }

        @Test
        void testShouldRefreshAuthor() {

                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(authores)
                                .subjects(subjects)
                                .priceByChannels(Collections.emptyList())
                                .build();

                List<Book> booksMock = new ArrayList<Book>();
                booksMock.add(book);
                when(this.repository.findAll()).thenReturn(booksMock);

                // Action
                this.bookService.refreshAuthor("123", "Juliana");

                // Asserts
                verify(this.repository, times(1)).findAll();
                verify(this.repository, times(1)).save(any());

        }

        @Test
        void testShouldRefreshSubject() {

                Book book = Book.builder()
                                .codL("123")
                                .edition(2)
                                .publishingCompany("Marvel")
                                .title("End game")
                                .releaseYear("2024")
                                .authors(authores)
                                .subjects(subjects)
                                .priceByChannels(Collections.emptyList())
                                .build();

                List<Book> booksMock = new ArrayList<Book>();
                booksMock.add(book);
                when(this.repository.findAll()).thenReturn(booksMock);

                // Action
                this.bookService.refreshSubject("123", "Adventure");

                // Asserts
                verify(this.repository, times(1)).findAll();
                verify(this.repository, times(1)).save(any());

        }
        /*
         * 
         * 
         * void refreshSubject(String codAs, String description);
         * 
         */

}
