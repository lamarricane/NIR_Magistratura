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

@WebServlet("")
public class StartPage extends HttpServlet {

    SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        List<Author> authors = new AuthorService(new AuthorDaoImpl(sessionFactory)).findAllAuthors();
        List<Book> books = new BookService(new BookDaoImpl(sessionFactory)).findAllBooks();
        List<Publisher> publishers = new PublisherService(new PublisherDaoImpl(sessionFactory)).findAllPublishers();
        req.setAttribute("books", books);
        req.setAttribute("authors", authors);
        req.setAttribute("publishers", publishers);
        getServletContext().getRequestDispatcher("/start-page.jsp").forward(req, resp);
        long endTime = System.currentTimeMillis();
        long SystemTime = endTime - startTime;
        System.out.println("Время выполнения метода: " + SystemTime + " миллисекунд");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

    }
}
