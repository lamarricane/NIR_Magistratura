package service;

import dao.impl.ReviewDaoImpl;
import model.Book;
import model.Review;

import java.util.List;

public class ReviewService {
    private final ReviewDaoImpl reviewDao;

    public ReviewService(ReviewDaoImpl reviewDao) {
        this.reviewDao = reviewDao;
    }

    public Review findReviewById(int id) {
        return reviewDao.findById(id);
    }

    public void saveReview(Review review) {
        reviewDao.save(review);
    }

    public void updateReviewById(Review review) {
        reviewDao.update(review);
    }

    public void deleteReviewById(Review review) {
        reviewDao.delete(review);
    }

    public List<Review> findAllReviews() {
        return reviewDao.findAll();
    }
}