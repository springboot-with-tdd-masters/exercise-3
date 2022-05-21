package com.softvision.books.services.domain;

import java.util.List;

public class Pagination<T> {

    private List<T> contents;

    private PageBean page;

    public Pagination(List<T> contents, PageBean pageBean) {
        this.contents = contents;
        this.page = pageBean;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }
    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean pageBean) {
        this.page = pageBean;
    }

    public static <T> Pagination<T> of(List<T> contents, PageBean pageBean) {
        return new Pagination<>(contents, pageBean);
    }
}
