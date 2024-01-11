package service;

import dao.impl.WebUserDaoImpl;
import model.WebUser;

import java.util.List;

public class WebUserService {
    private final WebUserDaoImpl webUserDao;

    public WebUserService(WebUserDaoImpl webUserDao) {
        this.webUserDao = webUserDao;
    }

    public WebUser findWebUserById(int id) {
        return webUserDao.findById(id);
    }

    public void saveWebUser(WebUser webUser) {
        webUserDao.save(webUser);
    }

    public void updateWebUser(WebUser webUser) {
        webUserDao.update(webUser);
    }

    public void deleteWebUser(WebUser webUser) {
        webUserDao.delete(webUser);
    }

    public List<WebUser> findAllWebUsers() {
        return webUserDao.findAll();
    }
}