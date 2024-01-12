package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@SequenceGenerator(name = "review_id_seq", sequenceName = "review_id_seq", allocationSize = 1)
@Entity
@Table(name = "Review")
public class Review {
    @Setter
    @Getter
    @Id
    @GeneratedValue(generator = "review_id_seq")
    @Column(name = "id")
    private int id;
    @Setter
    @Getter
    @Column(name = "text")
    private String text;
    @Column(name = "publication_date")
    private String publication_date;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "web_user_id")
    private WebUser webUser;
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void setPublicationDate(String publication_date){
        this.publication_date = publication_date;
    }

    public String getPublicationDate(){
        return publication_date;
    }

    public Review(String text, String publication_date) {
        this.text = text;
        this.publication_date = publication_date;
    }

    @Override
    public String toString() {
        return "Review: " +
                "\n Text: " + text +
                "\n Publication Date: " + publication_date;
    }
}