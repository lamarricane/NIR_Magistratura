package service;

import dao.impl.BookDaoImpl;
import model.Book;

import java.util.List;

public class BookService {
    private final BookDaoImpl bookDao;

    public BookService(BookDaoImpl bookDao) {
        this.bookDao = bookDao;
    }

    public Book findBookById(int id) {
        return bookDao.findById(id);
    }

    public void saveBook(Book book) {
        bookDao.save(book);
    }

    public void updateBook(Book book) {
        bookDao.update(book);
    }

    public void deleteBook(Book book) {
        bookDao.delete(book);
    }

    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }
}