package servlet;

import dao.impl.BookDaoImpl;
import dao.impl.AuthorDaoImpl;
import model.Author;
import model.Book;
import org.hibernate.SessionFactory;
import service.AuthorService;
import service.BookService;
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
        try {
            BookService service = new BookService(new BookDaoImpl(sessionFactory));
            books = service.findAllBooks();
            req.setAttribute("books", books);
            getServletContext().getRequestDispatcher("/change-book.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BookService service = new BookService(new BookDaoImpl(sessionFactory));
            AuthorService authorService = new AuthorService(new AuthorDaoImpl(sessionFactory));
            if (!req.getParameterMap().isEmpty()) {
                if (!req.getParameter("deleteName").isEmpty()) {
                    String deleteName = req.getParameter("deleteName");
                    Book book = checkBook(deleteName);
                    if (book != null) {
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
                        String newNumberOfPages = req.getParameter("updateNumberOfPages");
                        String newPublishingHouseId = req.getParameter("updatePublishingHouseId");
                        String newYearOfPublishing = req.getParameter("updateYearOfPublishing");
                        if (checkBook(book.getId(), newName, newGenre, newAuthor,newNumberOfPages, newPublishingHouseId, newYearOfPublishing)) {
                            book = service.findBookById(book.getId());
                            setNewParams(book, newName, newGenre, newAuthor, newNumberOfPages, newPublishingHouseId, newYearOfPublishing, authorService.findAllAuthors());
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
                    int number_of_pages = Integer.parseInt(req.getParameter("insertNumberOfPages"));
                    int publishing_house_id = Integer.parseInt(req.getParameter("insertPublishingHouseId"));
                    int year_of_publishing = Integer.parseInt(req.getParameter("insertYeaOfPublishing"));
                    if (checkParams(name, genre, author, String.valueOf(number_of_pages), String.valueOf(publishing_house_id), String.valueOf(year_of_publishing)) && checkBook(name) == null) {
                        List<Author> authors = authorService.findAllAuthors();
                        int count = 0;
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                Book book = new Book(name, genre, number_of_pages, publishing_house_id, year_of_publishing);
                                book.setAuthor(a);
                                a.addBook(book);
                                service.saveBook(book);
                                break;
                            }
                            count++;
                        }
                        if (count == authors.size()) {
                            throw new RuntimeException("Данный автор отсутствует!");
                        }
                    } else {
                        throw new RuntimeException("Неверные данные в поле для создания книг!");
                    }
                }
                resp.sendRedirect("/book");
            }
        } catch(Exception exception){
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                throw new RuntimeException(exception.getMessage());
            }
        }

        private void setNewParams (Book book, String newName, String newGenre, String newAuthor, String newNumberOfPages, String newPublishingHouseId, String newYearOfPublishing,  List<Author > authors){
            for (Author author : authors) {
                if (author.getName().equals(newAuthor)) {
                    if (book.getAuthor() != author) {
                        book.setAuthor(author);
                        author.addBook(book);
                        break;
                    }
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
           if (!Objects.equals(book.getPublishingHouseId(), newPublishingHouseId) && !(newPublishingHouseId).isEmpty()) {
                book.setPublishingHouseId(Integer.parseInt(newPublishingHouseId));
            }
            if (!Objects.equals(book.getYearOfPublishing(), newYearOfPublishing) && !(newYearOfPublishing).isEmpty()) {
                book.setYearOfPublishing(Integer.parseInt(newYearOfPublishing));
            }
        }

        private boolean checkBook ( int id, String newName, String newGenre, String newAuthor, String newNumberOfPages, String newPublishingHouseId, String newYeaOfPublishing) {
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
                    if (Objects.equals(book.getNumberOfPages(), newNumberOfPages)) {
                        count++;
                    }

                    if (Objects.equals(book.getPublishingHouseId(), newPublishingHouseId)) {
                            count++;
                    }
                    if (Objects.equals(book.getYearOfPublishing(), newYeaOfPublishing)) {
                            count++;
                    }


                    }
                }
                return count != 6;
            }

            private Book checkBook (String name){
                for (Book book : books) {
                    if (book.getName().equals(name)) {
                        return book;
                    }
                }
                return null;
            }

            private boolean checkParams (String name, String genre, String author, String number_of_pages, String
            publishing_house_id, String year_of_publishing){
                return !name.isEmpty() && !genre.isEmpty() && !author.isEmpty() && !number_of_pages.isEmpty() && !publishing_house_id.isEmpty() && !year_of_publishing.isEmpty();
            }
        }


