package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
@Entity
@Table(name = "Author")
public class Author {
    @Setter
    @Getter
    @Id
    @GeneratedValue(generator = "author_id_seq")
    @Column(name = "id")
    private int id;
    @Setter
    @Getter
    @Column(name = "name")
    private String name;
    @Column(name = "birth_date")
    private String birth_date;

    @Setter
    @Getter
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author(String name, String birth_date) {
        this.name = name;
        this.birth_date = birth_date;
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        book.setAuthor(this);
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void setBirthDate(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getBirthDate() {
        return birth_date;
    }

    @Override
    public String toString() {
        return "Author: " +
                "\n Name: " + name +
                "\n Birth Date: " + birth_date;
    }
}