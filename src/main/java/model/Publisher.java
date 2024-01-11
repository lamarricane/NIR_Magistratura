package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "publisher_id_seq", sequenceName = "publisher_id_seq", allocationSize = 1)
@Entity
@Table(name = "Publisher")
public class Publisher {
    @Id
    @GeneratedValue(generator = "publisher_id_seq")
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        book.setPublisher(this);
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public String toString() {
        return "Publisher: " +
                "\n Name: " + name +
                "\n Location: " + location;
    }
}


