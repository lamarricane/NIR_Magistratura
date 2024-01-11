package servlet;

import dao.impl.PublisherDaoImpl;
import model.Publisher;
import org.hibernate.SessionFactory;

import service.PublisherService;
import utils.HibernateSessionFactoryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/publisher")
public class PublisherServlet extends HttpServlet {

    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private List<Publisher> publishers;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            PublisherService service = new PublisherService(new PublisherDaoImpl(sessionFactory));
            publishers = service.findAllPublishers();
            req.setAttribute("publishers", publishers);
            getServletContext().getRequestDispatcher("/change-publisher.jsp").forward(req, resp);
            long endTime = System.currentTimeMillis();
            long PublisherTime = endTime - startTime;
            System.out.println("Время выполнения метода: " + PublisherTime + " миллисекунд");

        } catch (IOException | ServletException e) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(e.getMessage());

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PublisherService service = new PublisherService(new PublisherDaoImpl(sessionFactory));

            if (!req.getParameterMap().isEmpty()) {
                if (!req.getParameter("deleteName").isEmpty()) {
                    String deleteName = req.getParameter("deleteName");
                    Publisher publisher = checkPublisher(deleteName);
                    if (publisher != null) {
                        publisher.setBooks(null);
                        service.deletePublisher(service.findPublisherById(publisher.getId()));
                    } else {
                        throw new RuntimeException("Данный издатель отсутствует!");
                    }
                }
                if (!req.getParameter("updateName").isEmpty()) {
                    String updateName = req.getParameter("updateName");
                    String newName = req.getParameter("newName");
                    String newLocation = req.getParameter("newLocation");
                    Publisher publisher = checkPublisher(updateName);
                    if (publisher != null) {
                        publisher = service.findPublisherById(publisher.getId());
                        if (!newName.isEmpty()) {
                            publisher.setName(newName);
                            service.updatePublisher(publisher);
                        }
                        if (!newLocation.isEmpty()) {
                            publisher = service.findPublisherById(publisher.getId());
                            publisher.setLocation(newLocation);
                            service.updatePublisher(publisher);
                        }
                    } else {
                        throw new RuntimeException("Неверные данные в поле для изменения издателя книг!");
                    }
                }
                if (!req.getParameter("insertName").isEmpty()) {
                    String name = req.getParameter("insertName");
                    String location = req.getParameter("insertLocation");
                    if (checkPublisher(name) == null && !name.isEmpty()) {
                        service.savePublisher(new Publisher(name, location));
                    } else {
                        throw new RuntimeException("Неверные данные в поле для создания издателя книг!");
                    }
                }
                resp.sendRedirect("/publisher");
            }
        } catch (Exception exception) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(exception.getMessage());
        }
    }

    private Publisher checkPublisher(String name) {
        for (Publisher publisher : publishers) {
            if (publisher.getName().equals(name)) {
                return publisher;
            }
        }
        return null;
    }
}
