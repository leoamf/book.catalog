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

import com.challenge.book.catalog.dto.SubjectRequestDTO;
import com.challenge.book.catalog.exception.RecordNotFoundException;
import com.challenge.book.catalog.model.Subject;
import com.challenge.book.catalog.repository.SubjectRepository;
import com.challenge.book.catalog.service.SubjectService;
import com.challenge.book.catalog.service.impl.SubjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

        private SubjectService service;
        private SubjectRepository repository;

        @BeforeEach
        void beforeCreatePageRequest() {
                this.repository = Mockito.spy(SubjectRepository.class);
                this.service = new SubjectServiceImpl(this.repository);
        }

        @Test
        void testShouldCreateOneSubject() {
                // Arrange
                Subject subject = Subject.builder()
                                .codAs("123")
                                .description("Ficcao")
                                .build();
                SubjectRequestDTO dto = SubjectRequestDTO.builder()
                                .description("Ficcao")
                                .build();

                when(this.repository.save(any())).thenReturn(subject);

                // Action
                Subject subjectCreated = this.service.create(dto);

                // Asserts
                verify(this.repository, times(1)).save(any(Subject.class));
                Assertions.assertThat(subjectCreated).isNotNull();
                Assertions.assertThat(subjectCreated.getDescription()).isEqualTo(subject.getDescription());
        }

        @Test
        void testShouldUpdateOneSubject() {
                // Arrange
                Subject subject = Subject.builder()
                                .codAs("123")
                                .description("Romance")
                                .build();

                SubjectRequestDTO dto = SubjectRequestDTO.builder()
                                .description("FiRomanceccao")
                                .build();

                when(this.repository.save(any())).thenReturn(subject);
                when(this.repository.findById("123")).thenReturn(Optional.of(
                                subject));

                // Action
                Subject subjectUpdated = this.service.update("123", dto);

                // Asserts
                verify(this.repository, times(1)).findById("123");
                verify(this.repository, times(1)).save(any(Subject.class));
                Assertions.assertThat(subjectUpdated).isNotNull();
                Assertions.assertThat(subjectUpdated.getDescription()).isEqualTo(subject.getDescription());

        }

        @Test
        void testShouldUpdateWithRecordNotFoundException() {
                // Arrange
                SubjectRequestDTO dto = SubjectRequestDTO.builder()
                                .description("Romance")
                                .build();

                // Action
                Exception exception = assertThrows(RecordNotFoundException.class, () -> {
                        this.service.update("123", dto);
                });
                // Asserts
                assertTrue(exception instanceof RecordNotFoundException);
        }

        @Test
        void testShouldDeleteOneSubject() {
                // Arrange
                Subject subject = Subject.builder()
                                .codAs("123")
                                .description("Romance")
                                .build();
                SubjectRequestDTO dto = SubjectRequestDTO.builder()
                                .description("Romance")
                                .build();

                when(this.repository.save(any())).thenReturn(subject);
                when(this.repository.findById("123")).thenReturn(Optional.of(
                                subject));

                // Action
                Subject subjectCreated = this.service.create(dto);
                this.service.delete(subjectCreated.getCodAs());

                // Asserts
                verify(this.repository, times(1)).findById("123");
                verify(this.repository, times(1)).deleteById("123");
        }

        @Test
        void testShouldDeleteWithRecordNotFoundException() {
                // Arrange
                Subject subject = Subject.builder()
                                .codAs("123")
                                .description("Romance")
                                .build();

                // Action
                Exception exception = assertThrows(RecordNotFoundException.class, () -> {
                        this.service.delete(subject.getCodAs());
                });
                // Asserts
                assertTrue(exception instanceof RecordNotFoundException);
        }

        @Test
        void testShouldGetListOfAllSubjects() {
                // Arrange
                Subject subject = Subject.builder()
                                .codAs("123")
                                .description("Romance")
                                .build();

                List<Subject> subjectsMock = new ArrayList<Subject>();
                subjectsMock.add(subject);
                when(this.repository.findAll()).thenReturn(subjectsMock);

                // Action
                List<Subject> subjects = this.service.gettAll();

                // Asserts
                verify(this.repository, times(1)).findAll();
                Assertions.assertThat(subjects.size()).isEqualTo(1);
        }

}
