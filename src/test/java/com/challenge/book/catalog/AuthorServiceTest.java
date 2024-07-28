package com.challenge.book.catalog;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.book.catalog.dto.AuthorRequestDTO;
import com.challenge.book.catalog.exception.RecordNotFoundException;
import com.challenge.book.catalog.model.Author;
import com.challenge.book.catalog.repository.AuthorRepository;
import com.challenge.book.catalog.service.AuthorService;
import com.challenge.book.catalog.service.impl.AuthorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

        private AuthorService service;
        private AuthorRepository repository;

        @BeforeEach
        void beforeCreatePageRequest() {
                this.repository = Mockito.spy(AuthorRepository.class);
                this.service = new AuthorServiceImpl(this.repository);
        }

        @Test
        void testShouldCreateOneAuthor() {
                // Arrange
                Author author = Author.builder()
                                .codAu("123")
                                .name("Leonardo Freitas")
                                .build();
                AuthorRequestDTO dto = AuthorRequestDTO.builder()
                                .name("Leonardo Freitas")
                                .build();

                when(this.repository.save(any())).thenReturn(author);

                // Action
                Author authorCreated = this.service.create(dto);

                // Asserts
                verify(this.repository, times(1)).save(any(Author.class));
                Assertions.assertThat(authorCreated).isNotNull();
                Assertions.assertThat(authorCreated.getName()).isEqualTo(author.getName());
        }

        @Test
        void testShouldUpdateOneAuthor() {
                // Arrange
                Author author = Author.builder()
                                .codAu("123")
                                .name("Freitas")
                                .build();

                AuthorRequestDTO dto = AuthorRequestDTO.builder()
                                .name("Freitas")
                                .build();

                when(this.repository.save(any())).thenReturn(author);
                when(this.repository.findById("123")).thenReturn(Optional.of(
                                author));

                // Action
                Author authorUpdated = this.service.update("123", dto);

                // Asserts
                verify(this.repository, times(1)).findById("123");
                verify(this.repository, times(1)).save(any(Author.class));
                Assertions.assertThat(authorUpdated).isNotNull();
                Assertions.assertThat(authorUpdated.getName()).isEqualTo(author.getName());

        }

        @Test
        void testShouldUpdateWithRecordNotFoundException() {
                // Arrange
                AuthorRequestDTO dto = AuthorRequestDTO.builder()
                                .name("Freitas")
                                .build();

                // Action
                Exception exception = assertThrows(RecordNotFoundException.class, () -> {
                        this.service.update("123", dto);
                });
                // Asserts
                assertTrue(exception instanceof RecordNotFoundException);
        }

        @Test
        void testShouldDeleteOneAuthor() {
                // Arrange
                Author author = Author.builder()
                                .codAu("123")
                                .name("Freitas")
                                .build();
                AuthorRequestDTO dto = AuthorRequestDTO.builder()
                                .name("Freitas")
                                .build();

                when(this.repository.save(any())).thenReturn(author);
                when(this.repository.findById("123")).thenReturn(Optional.of(
                                author));

                // Action
                Author authorCreated = this.service.create(dto);
                this.service.delete(authorCreated.getCodAu());

                // Asserts
                verify(this.repository, times(1)).findById("123");
                verify(this.repository, times(1)).deleteById("123");
        }

        @Test
        void testShouldDeleteWithRecordNotFoundException() {
                // Arrange
                Author author = Author.builder()
                                .codAu("123")
                                .name("Freitas")
                                .build();

                // Action
                Exception exception = assertThrows(RecordNotFoundException.class, () -> {
                        this.service.delete(author.getCodAu());
                });
                // Asserts
                assertTrue(exception instanceof RecordNotFoundException);
        }

        @Test
        void testShouldGetListOfAllAuthors() {
                // Arrange
                Author author = Author.builder()
                                .codAu("123")
                                .name("Freitas")
                                .build();

                List<Author> authorsMock = new ArrayList<Author>();
                authorsMock.add(author);
                when(this.repository.findAll()).thenReturn(authorsMock);

                // Action
                List<Author> authors = this.service.getAll();

                // Asserts
                verify(this.repository, times(1)).findAll();
                Assertions.assertThat(authors.size()).isEqualTo(1);
        }

}
