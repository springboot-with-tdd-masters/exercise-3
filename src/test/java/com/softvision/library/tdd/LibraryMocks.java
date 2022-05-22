package com.softvision.library.tdd;

import com.softvision.library.tdd.model.Author;
import com.softvision.library.tdd.model.Book;

public class LibraryMocks {
    public static final long MOCK_BOOK_ID_1 = 1L;
    public static final String MOCK_TITLE_1 = "The Art Of War";

    public static final String MOCK_AUTHOR_1 = "Sun Tzu";

    public static final long MOCK_BOOK_ID_2 = 2L;
    public static final String MOCK_TITLE_2 = "The Prince";

    public static final String MOCK_AUTHOR_2 = "Niccolo Machiavelli";

    private LibraryMocks() {}

    public static Book getMockBook1() {
        return new Book(MOCK_TITLE_1);
    }

    public static Book getMockBook2() {
        return new Book(MOCK_TITLE_2);
    }

    public static Author getMockAuthor1() {
        return new Author(MOCK_AUTHOR_1);
    }

    public static Author getMockAuthor2() {
        return new Author(MOCK_AUTHOR_2);
    }
}
