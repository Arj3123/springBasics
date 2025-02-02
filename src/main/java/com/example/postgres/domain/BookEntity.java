package com.example.postgres.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tables")
public class BookEntity {
    @Id
    private String isbn;
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)  // if we get a book back we also get the author back and if we make any changes to that author it will be shown in the database
    @JoinColumn(name = "author_id")
    private Author author;
}
