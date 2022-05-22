package com.softvision.library.tdd.controller;

import com.softvision.library.tdd.model.Book;
import com.softvision.library.tdd.model.RecordNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static com.softvision.library.tdd.LibraryMocks.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BookControllerTests extends AbstractControllerTests {

    @Test
    @DisplayName("Given a successful service getAll, response should give http status 200 with the list.")
    void test_getAll_success() throws Exception {
        when(bookService.getAll(any())).thenReturn(createPage(Arrays.asList(getMockBook1(), getMockBook2())));

        mockMvc.perform(get("/books"))
                .andExpect(jsonPath("$.content[0].title", Matchers.is(MOCK_TITLE_1)))
                .andExpect(jsonPath("$.content[1].title", Matchers.is(MOCK_TITLE_2)));

        verify(bookService, atMostOnce()).getAll();
    }

    @Test
    @DisplayName("Given a blank result from service getAll, response should give http status 404 (not found).")
    void test_getAll_fail() throws Exception {
        when(bookService.getAll(any())).thenThrow(RecordNotFoundException.class);

        mockMvc.perform(get("/books")).andExpect(status().isNotFound());

        verify(bookService, atMostOnce()).getAll();
    }

    @Test
    @DisplayName("Given a successful result from createOrUpdate, response should give http status 201 (created).")
    void test_create() throws Exception {
        Book book = getMockBook1();
        when(bookService.createOrUpdate(argThat(b -> b.getTitle().equals(MOCK_TITLE_1))))
                .thenReturn(book);

        mockMvc.perform(post("/books").content(objectMapper.writeValueAsBytes(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title", Matchers.is(MOCK_TITLE_1)));
    }

    @Test
    @DisplayName("Given a failure result from createOrUpdate, response should give http status 5xx (server error).")
    void test_create_fail() throws Exception {
        Book book = getMockBook1();
        when(bookService.createOrUpdate(argThat(b -> b.getTitle().equals(MOCK_TITLE_1))))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/books").content(objectMapper.writeValueAsBytes(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    private Page<Book> createPage(List<Book> content) {
        return new Page<>() {
            @Override
            public Iterator<Book> iterator() {
                return getContent().listIterator();
            }

            @Override
            public void forEach(Consumer<? super Book> action) {
                Page.super.forEach(action);
            }

            @Override
            public Spliterator<Book> spliterator() {
                return Page.super.spliterator();
            }

            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Book, ? extends U> converter) {
                return null;
            }

            @Override
            public Stream<Book> stream() {
                return Page.super.stream();
            }

            @Override
            public <R> Streamable<R> flatMap(Function<? super Book, ? extends Stream<? extends R>> mapper) {
                return Page.super.flatMap(mapper);
            }

            @Override
            public Streamable<Book> filter(Predicate<? super Book> predicate) {
                return Page.super.filter(predicate);
            }

            @Override
            public boolean isEmpty() {
                return Page.super.isEmpty();
            }

            @Override
            public Streamable<Book> and(Supplier<? extends Stream<? extends Book>> stream) {
                return Page.super.and(stream);
            }

            @Override
            public Streamable<Book> and(Book... others) {
                return Page.super.and(others);
            }

            @Override
            public Streamable<Book> and(Iterable<? extends Book> iterable) {
                return Page.super.and(iterable);
            }

            @Override
            public Streamable<Book> and(Streamable<? extends Book> streamable) {
                return Page.super.and(streamable);
            }

            @Override
            public List<Book> toList() {
                return Page.super.toList();
            }

            @Override
            public Set<Book> toSet() {
                return Page.super.toSet();
            }

            @Override
            public Stream<Book> get() {
                return Page.super.get();
            }

            @Override
            public Pageable nextOrLastPageable() {
                return Page.super.nextOrLastPageable();
            }

            @Override
            public Pageable previousOrFirstPageable() {
                return Page.super.previousOrFirstPageable();
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Book> getContent() {
                return content;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return getContent().iterator().hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable getPageable() {
                return Page.super.getPageable();
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }
        };
    }
}
