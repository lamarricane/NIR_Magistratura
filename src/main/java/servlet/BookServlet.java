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
import java.util.Objects;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private List<Book> books;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            BookService service = new BookService(new BookDaoImpl(sessionFactory));
            books = service.findAllBooks();
            req.setAttribute("books", books);
            getServletContext().getRequestDispatcher("/change-book.jsp").forward(req, resp);
            long endTime = System.currentTimeMillis();
            long BookTime = endTime - startTime;
            System.out.println("Время выполнения метода: " + BookTime + " миллисекунд");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BookService service = new BookService(new BookDaoImpl(sessionFactory));
            AuthorService authorService = new AuthorService(new AuthorDaoImpl(sessionFactory));
            PublisherService publisherService = new PublisherService(new PublisherDaoImpl(sessionFactory));
            if (!req.getParameterMap().isEmpty()) {
                if (!req.getParameter("deleteName").isEmpty()) {
                    String deleteName = req.getParameter("deleteName");
                    Book book = checkBook(deleteName);
                    if (book != null) {
                        book.setReviews(null);
                        service.deleteBook(book);
                    } else {
                        throw new RuntimeException("Неверные данные в поле для удаления книги!");
                    }
                }
                if (!req.getParameter("updateName").isEmpty()) {
                    String updateName = req.getParameter("updateName");
                    Book book = checkBook(updateName);
                    if (book != null) {
                        String newName = req.getParameter("newName");
                        String newGenre = req.getParameter("updateGenre");
                        String newAuthor = req.getParameter("updateAuthor");
                        String newPublisher = req.getParameter("updatePublisher");
                        String newNumberOfPages = req.getParameter("updateNumberOfPages");
                        String newYearOfPublishing = req.getParameter("updateYearOfPublishing");
                        if (checkBook(book.getId(), newName, newGenre, newAuthor, newPublisher, newNumberOfPages, newYearOfPublishing)) {
                            book = service.findBookById(book.getId());
                            setNewParams(book, newName, newGenre, newAuthor, newPublisher, newNumberOfPages, newYearOfPublishing, authorService.findAllAuthors(), publisherService.findAllPublishers());
                            service.updateBook(book);
                        } else {
                            throw new RuntimeException("Неверные данные в поле для изменения автора книг!");
                        }
                    }
                }
                if (!req.getParameter("insertName").isEmpty()) {
                    String name = req.getParameter("insertName");
                    String genre = req.getParameter("insertGenre");
                    String author = req.getParameter("insertAuthor");
                    String publisher = req.getParameter("insertPublisher");
                    int number_of_pages = Integer.parseInt(req.getParameter("insertNumberOfPages"));
                    int year_of_publishing = Integer.parseInt(req.getParameter("insertYearOfPublishing"));
                    if (checkParams(name, genre, author, publisher, String.valueOf(number_of_pages), String.valueOf(year_of_publishing)) && checkBook(name) == null) {
                        List<Author> authors = authorService.findAllAuthors();
                        List<Publisher> publishers = publisherService.findAllPublishers();
                        int count1 = 0;
                        int count2 = 0;
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                for (Publisher b : publishers) {
                                    if (b.getName().equals(publisher)) {
                                        Book book = new Book(name, genre, number_of_pages, year_of_publishing);
                                        book.setAuthor(a);
                                        book.setPublisher(b);
                                        a.addBook(book);
                                        b.addBook(book);
                                        service.saveBook(book);
                                        break;
                                    }
                                    count2++;
                                }
                                if (count2 == publishers.size()) {
                                    throw new RuntimeException("Данный издатель отсутствует!");
                                }
                                break;
                            }
                            count1++;
                        }
                        if (count1 == authors.size()) {
                            throw new RuntimeException("Данный автор отсутствует!");
                        }
                    } else {
                        throw new RuntimeException("Неверные данные в поле для создания книг!");
                    }
                }
                resp.sendRedirect("/book");
            }
        } catch (Exception exception) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void setNewParams(Book book, String newName, String newGenre, String newAuthor, String newPublisher, String newNumberOfPages, String newYearOfPublishing, List<Author> authors, List<Publisher> publishers) {
        for (Author author : authors) {
            if (author.getName().equals(newAuthor)) {
                //if (book.getAuthor() != author) {
                book.setAuthor(author);
                author.addBook(book);
                //break;
                //}
            }
        }
        for (Publisher publisher : publishers) {
            if (publisher.getName().equals(newPublisher)) {
                //if (book.getPublisher() != publisher) {
                book.setPublisher(publisher);
                publisher.addBook(book);
                //break;
                //}
            }
        }
        if (!book.getName().equals(newName) && !newName.isEmpty()) {
            book.setName(newName);
        }
        if (!book.getGenre().equals(newGenre) && !newGenre.isEmpty()) {
            book.setGenre(newGenre);
        }
        if (!Objects.equals(book.getNumberOfPages(), newNumberOfPages) && !(newNumberOfPages).isEmpty()) {
            book.setNumberOfPages(Integer.parseInt(newNumberOfPages));
        }
        if (!Objects.equals(book.getYearOfPublishing(), newYearOfPublishing) && !(newYearOfPublishing).isEmpty()) {
            book.setYearOfPublishing(Integer.parseInt(newYearOfPublishing));
        }
    }

    private boolean checkBook(int id, String newName, String newGenre, String newAuthor, String newPublisher, String newNumberOfPages, String newYeaOfPublishing) {
        int count = 0;
        for (Book book : books) {
            if (book.getId() == id) {
                if (book.getName().equals(newName)) {
                    count++;
                }
                if (book.getGenre().equals(newGenre)) {
                    count++;
                }
                if (book.getAuthor().getName().equals(newAuthor)) {
                    count++;
                }
                if (book.getPublisher().getName().equals(newPublisher)) {
                    count++;
                }
                if (Objects.equals(book.getNumberOfPages(), newNumberOfPages)) {
                    count++;
                }
                if (Objects.equals(book.getYearOfPublishing(), newYeaOfPublishing)) {
                    count++;
                }
            }
        }
        return count != 6;
    }

    private Book checkBook(String name) {
        for (Book book : books) {
            if (book.getName().equals(name)) {
                return book;
            }
        }
        return null;
    }

    private boolean checkParams(String name, String genre, String author, String publisher, String number_of_pages, String year_of_publishing) {
        return !name.isEmpty() && !genre.isEmpty() && !author.isEmpty() && !publisher.isEmpty() && !number_of_pages.isEmpty() && !year_of_publishing.isEmpty();
    }
}
