package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
@Entity
@Table(name = "Author")
public class Author {
    @Id //генерация первичного ключа id таблицы
    @GeneratedValue(generator = "author_id_seq")
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "birth_date")
    private String birth_date;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author(String name, String birth_date) {
        this.name = name;
        this.birth_date = birth_date;
        books = new ArrayList<>();
}


    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.setAuthor(this);
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getBirthDate() {
            return birth_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author: " +
                "\n Name: " + name +
                "\n Birth Date: " + birth_date;
    }
}
