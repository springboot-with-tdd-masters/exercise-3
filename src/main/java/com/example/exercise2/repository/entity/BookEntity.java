package com.example.exercise2.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
  private String title;
  private String author;

  public BookEntity() {
  }

  public BookEntity(Long id, String title, String author) {
    this.id = id;
    this.title = title;
    this.author = author;
  }

  public BookEntity(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "BookEntity{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        '}';
  }

}
