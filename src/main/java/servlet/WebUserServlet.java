package servlet;

import dao.impl.WebUserDaoImpl;
import model.WebUser;
import org.hibernate.SessionFactory;
import service.WebUserService;
import utils.HibernateSessionFactoryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/webUser")
public class WebUserServlet extends HttpServlet {

    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private List<WebUser> webUsers;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            WebUserService service = new WebUserService(new WebUserDaoImpl(sessionFactory));
            webUsers = service.findAllWebUsers();
            req.setAttribute("webUsers", webUsers);
            getServletContext().getRequestDispatcher("/change-web_user.jsp").forward(req, resp);
            long endTime = System.currentTimeMillis();
            long WebUserTime = endTime - startTime;
            System.out.println("Время выполнения метода: " + WebUserTime + " миллисекунд");

        } catch (IOException | ServletException e) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            WebUserService service = new WebUserService(new WebUserDaoImpl(sessionFactory));
            if (!req.getParameterMap().isEmpty()) {
                if (!req.getParameter("deleteName").isEmpty()) {
                    String deleteName = req.getParameter("deleteName");
                    WebUser webUser = checkWebUser(deleteName);
                    if (webUser != null) {
                        webUser.setReviews(null);
                        service.deleteWebUser(service.findWebUserById(webUser.getId()));
                    } else {
                        throw new RuntimeException("Пользователь отсутствует!");
                    }
                }
                if (!req.getParameter("updateName").isEmpty()) {
                    String updateName = req.getParameter("updateName");
                    String newName = req.getParameter("newName");
                    String newRegistrationDate = req.getParameter("newRegistrationDate");
                    WebUser webUser = checkWebUser(updateName);
                    if (webUser != null) {
                        webUser = service.findWebUserById(webUser.getId());
                        if (!newName.isEmpty()) {
                            webUser.setName(newName);
                            service.updateWebUser(webUser);
                        }
                        if (!newRegistrationDate.isEmpty()) {
                            webUser = service.findWebUserById(webUser.getId());
                            webUser.setRegistrationDate(newRegistrationDate);
                            service.updateWebUser(webUser);
                        }
                    } else {
                        throw new RuntimeException("Неверные данные в поле для изменения пользователя!");
                    }
                }
                if (!req.getParameter("insertName").isEmpty()) {
                    String name = req.getParameter("insertName");
                    String registration_date = req.getParameter("insertRegistrationDate");
                    if (checkWebUser(name) == null && !name.isEmpty()) {
                        service.saveWebUser(new WebUser(name, registration_date));
                    } else {
                        throw new RuntimeException("Неверные данные в поле для создания пользователя!");
                    }
                }
                resp.sendRedirect("/webUser");
            }
        } catch (Exception exception) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            throw new RuntimeException(exception.getMessage());
        }
    }

    private WebUser checkWebUser(String name) {
        for (WebUser webUser : webUsers) {
            if (webUser.getName().equals(name)) {
                return webUser;
            }
        }
        return null;
    }
}