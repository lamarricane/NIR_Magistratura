package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
@Entity
@Table(name = "Book")
public class Book {
    @Setter
    @Getter
    @Id
    @GeneratedValue(generator = "book_id_seq")
    @Column(name = "id")
    private int id;
    @Setter
    @Getter
    @Column(name = "name")
    private String name;
    @Setter
    @Getter
    @Column(name = "genre")
    private String genre;
    @Column(name = "number_of_pages")
    private int number_of_pages;
    @Column(name = "year_of_publishing")
    private int year_of_publishing;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public Book(String name, String genre, int number_of_pages, int year_of_publishing) {
        this.name = name;
        this.genre = genre;
        this.number_of_pages = number_of_pages;
        this.year_of_publishing = year_of_publishing;
    }

    public int getNumberOfPages() {
        return number_of_pages;
    }

    public void setNumberOfPages(int number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public int getYearOfPublishing() {
        return year_of_publishing;
    }

    public void setYearOfPublishing(int year_of_publishing) {
        this.year_of_publishing = year_of_publishing;
    }

    @Override
    public String toString() {
        return "Book: " +
                "\n Name: " + name +
                "\n Genre: " + genre +
                "\n Number of pages: " + number_of_pages +
                "\n Year of publishing: " + year_of_publishing;
    }
}


