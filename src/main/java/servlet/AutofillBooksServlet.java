package servlet;

import org.hibernate.SessionFactory;
import utils.HibernateSessionFactoryUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/autofillBooks")
public class AutofillBooksServlet extends HttpServlet {
    private final SessionFactory factory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    }
}
