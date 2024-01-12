package servlet;

import dao.impl.*;
import model.*;
import org.hibernate.SessionFactory;
import service.*;
import utils.HibernateSessionFactoryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private List<Review> reviews;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            ReviewService service = new ReviewService(new ReviewDaoImpl(sessionFactory));
            reviews = service.findAllReviews();
            req.setAttribute("reviews", reviews);
            getServletContext().getRequestDispatcher("/change-review.jsp").forward(req, resp);
            long endTime = System.currentTimeMillis();
            long ReviewTime = endTime - startTime;
            System.out.println("Время выполнения метода: " + ReviewTime + " миллисекунд");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReviewService service = new ReviewService(new ReviewDaoImpl(sessionFactory));
            WebUserService webUserService = new WebUserService(new WebUserDaoImpl(sessionFactory));
            BookService bookService = new BookService(new BookDaoImpl(sessionFactory));
            if (!req.getParameterMap().isEmpty()) {
                if (!req.getParameter("deleteReviewById").isEmpty()) {
                    String deleteReviewById = req.getParameter("deleteReviewById");
                    Review review = checkReview(Integer.parseInt(deleteReviewById));
                    if (review != null) {
                        service.deleteReviewById(review);
                    } else {
                        throw new RuntimeException("Неверные данные в поле для удаления отзыва!");
                    }
                }
                if (!req.getParameter("updateReviewById").isEmpty()) {
                    String updateReviewById = req.getParameter("updateReviewById");
                    Review review = checkReview(Integer.parseInt(updateReviewById));
                    if (review != null) {
                        String newBook = req.getParameter("updateBook");
                        String newText = req.getParameter("updateText");
                        String newWebUser = req.getParameter("updateWebUser");
                        String newPublicationDate = req.getParameter("updatePublicationDate");
                        if (checkReview(review.getId(), newBook, newText, newWebUser, newPublicationDate)) {
                            review = service.findReviewById(review.getId());
                            setNewParams(review, newBook, newText, newWebUser, newPublicationDate, webUserService.findAllWebUsers(), bookService.findAllBooks() );
                            service.updateReviewById(review);
                        } else {
                            throw new RuntimeException("Неверные данные в поле для изменения автора книг!");
                        }
                    }
                }
                if (!req.getParameter("insertText").isEmpty()) {
                    String book = req.getParameter("insertBook");
                    String text = req.getParameter("insertText");
                    String publication_date = req.getParameter("insertPublicationDate");
                    String webUser = req.getParameter("insertWebUser");
                    if (checkParams(book, text, webUser, publication_date) && checkReview1(text) == null) {
                        List<WebUser> webUsers = webUserService.findAllWebUsers();
                        List<Book> books = bookService.findAllBooks();
                        int count1 = 0;
                        int count2 = 0;
                        for (WebUser a : webUsers) {
                            if (a.getName().equals(webUser)) {
                                for (Book b : books) {
                                    if (b.getName().equals(book)) {
                                        Review review = new Review(text, publication_date);
                                        review.setWebUser(a);
                                        review.setBook(b);
                                        a.addReview(review);
                                        b.addReview(review);
                                        service.saveReview(review);
                                        break;
                                    }
                                    count2++;
                                }
                                if (count2 == books.size()) {
                                    throw new RuntimeException("Данная книга отсутствует!");
                                }
                                break;
                            }
                            count1++;
                        }
                        if (count1 == webUsers.size()) {
                            throw new RuntimeException("Данный пользователь отсутствует!");
                        }
                    } else {
                        throw new RuntimeException("Неверные данные в поле для создания отзыва!");
                    }
                }
                resp.sendRedirect("/review");
            }
        } catch (Exception exception) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(exception.getMessage());
        }
    }


    private void setNewParams(Review review, String newText, String newPublicationDate, String newWebUser, String newBook, List<WebUser> webUsers, List<Book> books) {
        for (Book book : books) {
            if (book.getName().equals(newBook)) {
                if (review.getBook() != book) {
                review.setBook(book);
                book.addReview(review);
                break;
                }
            }
        }
        if (!review.getText().equals(newText) && !newText.isEmpty()) {
            review.setText(newText);
        for (WebUser webUser : webUsers) {
            if (webUser.getName().equals(newWebUser)) {
                if (review.getWebUser() != webUser) {
                    review.setWebUser(webUser);
                    webUser.addReview(review);
                    break;
                }
            }
        }
        }
        if (!review.getPublicationDate().equals(newPublicationDate) && !newPublicationDate.isEmpty()) {
            review.setPublicationDate(newPublicationDate);
        }
    }

    private boolean checkReview(int id, String newText, String newPublicationDate, String newWebUser, String newBook) {
        int count = 0;
        for (Review review : reviews) {
            if (review.getId() == id) {
                if (review.getBook().getName().equals(newBook)) {
                    count++;
                }
                if (review.getText().equals(newText)) {
                    count++;
                }
                if (review.getWebUser().getName().equals(newWebUser)) {
                    count++;
                }
                if (review.getPublicationDate().equals(newPublicationDate)) {
                    count++;
                }
            }
        }
        return count != 4;
    }

    private Review checkReview(int id) {
        for (Review review : reviews) {
            if (review.getId() == (id)) {
                return review;
            }
        }
        return null;
    }

    private Review checkReview1(String text) {
        for (Review review : reviews) {
            if (review.getText().equals(text)) {
                return review;
            }
        }
        return null;
    }

    private boolean checkParams(String text, String publication_date, String webUser, String book) {
        return !text.isEmpty() && !publication_date.isEmpty() && !webUser.isEmpty() && !book.isEmpty();
    }
}
