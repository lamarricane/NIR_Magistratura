import dao.impl.BookDaoImpl;
import dao.impl.AuthorDaoImpl;
import model.Author;
import model.Book;
import org.hibernate.SessionFactory;
import service.AuthorService;
import service.BookService;
import utils.HibernateSessionFactoryUtil;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        AuthorService authorService = new AuthorService(new AuthorDaoImpl(sessionFactory));
        BookService bookService = new BookService(new BookDaoImpl(sessionFactory));

        Author firstAuthor = new Author("Fall Out Boy", "01.26.1993");
        Author secondAuthor = new Author("Imagine Dragons", "01.26.1993");

        Book firstBook = new Book("Immortals", "Rock", 123, 1, 2001);
        Book secondBook = new Book("Temptation", "Heavy Metal", 666, 2, 2023);
        Book thirdBook = new Book("Tinnitius", "Pop", 2237, 1, 1999);

        authorService.saveAuthor(firstAuthor);
        authorService.saveAuthor(secondAuthor);

        firstBook.setAuthor(firstAuthor);
        secondBook.setAuthor(firstAuthor);
        thirdBook.setAuthor(secondAuthor);

        firstAuthor.addBook(firstBook);
        firstAuthor.addBook(secondBook);
        secondAuthor.addBook(thirdBook);

        bookService.saveBook(firstBook);
        bookService.saveBook(secondBook);
        bookService.saveBook(thirdBook);


        List<Author> authorList = authorService.findAllAuthors();
        for (Author author : authorList) {
            System.out.println(author.toString());
        }

        List<Book> bookList = bookService.findAllBooks();
        for (Book book : bookList) {
            System.out.println(book.toString());
        }
    }
}
