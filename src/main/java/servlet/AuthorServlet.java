package servlet;

import dao.impl.AuthorDaoImpl;
import model.Author;
import org.hibernate.SessionFactory;

import service.AuthorService;
import utils.HibernateSessionFactoryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {

    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private List<Author> authors;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            AuthorService service = new AuthorService(new AuthorDaoImpl(sessionFactory));
            authors = service.findAllAuthors();
            req.setAttribute("authors", authors);
            getServletContext().getRequestDispatcher("/change-author.jsp").forward(req, resp);
            long endTime = System.currentTimeMillis();
            long AuthorTime = endTime - startTime;
            System.out.println("Время выполнения метода: " + AuthorTime + " миллисекунд");

        } catch (IOException | ServletException e) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AuthorService service = new AuthorService(new AuthorDaoImpl(sessionFactory));
            if (!req.getParameterMap().isEmpty()) {
                if (!req.getParameter("deleteName").isEmpty()) {
                    String deleteName = req.getParameter("deleteName");
                    Author author = checkAuthor(deleteName);
                    if (author != null) {
                        author.setBooks(null);
                        service.deleteAuthor(service.findAuthorById(author.getId()));
                    } else {
                        throw new RuntimeException("Данный автор отсутствует!");
                    }
                }
                if (!req.getParameter("updateName").isEmpty()) {
                    String updateName = req.getParameter("updateName");
                    String newName = req.getParameter("newName");
                    String newBirthDate = req.getParameter("newBirthDate");
                    Author author = checkAuthor(updateName);
                    if (author != null) {
                        author = service.findAuthorById(author.getId());
                        if (!newName.isEmpty()) {
                            author.setName(newName);
                            service.updateAuthor(author);
                        }
                        if (!newBirthDate.isEmpty()) {
                            author = service.findAuthorById(author.getId());
                            author.setBirthDate(newBirthDate);
                            service.updateAuthor(author);
                        }
                    } else {
                        throw new RuntimeException("Неверные данные в поле для изменения автора книг!");
                    }
                }
                if (!req.getParameter("insertName").isEmpty()) {
                    String name = req.getParameter("insertName");
                    String birth_date = req.getParameter("insertBirthDate");
                    if (checkAuthor(name) == null && !name.isEmpty()) {
                        service.saveAuthor(new Author(name, birth_date));
                    } else {
                        throw new RuntimeException("Неверные данные в поле для создания автора книг!");
                    }
                }
                resp.sendRedirect("/author");
            }
        } catch (Exception exception) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(exception.getMessage());
        }
    }

    private Author checkAuthor(String name) {
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                return author;
            }
        }
        return null;
    }
}
