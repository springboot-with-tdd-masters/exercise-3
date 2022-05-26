package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name="author")
public class Author extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    @JsonIgnore
    @OneToMany(mappedBy="author")
    private Set<Book> books;

    private String name;

}
