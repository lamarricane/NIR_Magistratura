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
@SequenceGenerator(name = "web_user_id_seq", sequenceName = "web_user_id_seq", allocationSize = 1)
@Entity
@Table(name = "WebUser")
public class WebUser {
    @Setter
    @Getter
    @Id
    @GeneratedValue(generator = "web_user_id_seq")
    @Column(name = "id")
    private int id;
    @Setter
    @Getter
    @Column(name = "name")
    private String name;
    @Column(name = "registration_date")
    private String registration_date;

    @Setter
    @Getter
    @OneToMany(mappedBy = "webUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;

    public WebUser(String name, String registration_date) {
        this.name = name;
        this.registration_date = registration_date;
        reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        review.setWebUser(this);
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public void setRegistrationDate(String registration_date) {
        this.registration_date = registration_date;
    }

    public String getRegistrationDate() {
        return registration_date;
    }

    @Override
    public String toString() {
        return "WebUser: " +
                "\n Name: " + name +
                "\n Registration Date: " + registration_date;
    }
}