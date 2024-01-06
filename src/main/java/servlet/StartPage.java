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

@WebServlet("")
public class StartPage extends HttpServlet {

    SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Author> authors = new AuthorService(new AuthorDaoImpl(sessionFactory)).findAllAuthors();
        List<Book> books = new BookService(new BookDaoImpl(sessionFactory)).findAllBooks();
        req.setAttribute("books", books);
        req.setAttribute("authors", authors);
        getServletContext().getRequestDispatcher("/start-page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
