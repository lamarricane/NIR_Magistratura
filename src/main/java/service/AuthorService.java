package service;

import dao.impl.AuthorDaoImpl;
import model.Author;

import java.util.List;

public class AuthorService {
    private final AuthorDaoImpl authorDao;

    public AuthorService(AuthorDaoImpl authorDao) {
        this.authorDao = authorDao;
    }

    public Author findAuthorById(int id) {
        return authorDao.findById(id);
    }

    public void saveAuthor(Author author) {
        authorDao.save(author);
    }

    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    public void deleteAuthor(Author author) {
        authorDao.delete(author);
    }

    public List<Author> findAllAuthors() {
        return authorDao.findAll();
    }
}
