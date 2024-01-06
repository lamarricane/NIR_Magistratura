package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(generator = "book_id_seq")
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "genre")
    private String genre;
    @Column(name = "number_of_pages")
    private int number_of_pages;
    @Column(name = "publishing_house_id")
    private int publishing_house_id;
    @Column(name = "year_of_publishing")
    private int year_of_publishing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;


    public Book(String name, String genre, int number_of_pages, int publishing_house_id, int year_of_publishing) {
        this.name = name;
        this.genre = genre;
        this.number_of_pages = number_of_pages;
        this.publishing_house_id = publishing_house_id;
        this.year_of_publishing = year_of_publishing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return number_of_pages;
    }

    public void setNumberOfPages(int number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public int getPublishingHouseId() {
        return publishing_house_id;
    }

    public void setPublishingHouseId(int publishing_house_id) {
        this.publishing_house_id = publishing_house_id;
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
                "\n Publishing house id: " + publishing_house_id +
                "\n Year of publishing: " + year_of_publishing;
    }
}
