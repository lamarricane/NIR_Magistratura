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

        Author firstAuthor = new Author("Mary Stewart", "01.03.1933");
        Author secondAuthor = new Author("Sarah Gio", "30.04.1977");
        Author thirdAuthor = new Author("Rachel Caine", "26.10.1960");

        Book firstBook = new Book("A fire in the night", "Detective", 322, 1, 1990);
        Book secondBook = new Book("Abode of thorns", "Romance", 536, 1, 1989);
        Book thirdBook = new Book("The last camellia", "Adventures", 298, 2, 2004);
        Book fourthBook = new Book("Wolf River", "Detective", 450, 2, 2013);
        Book fifthBook = new Book("Gloomy Bay", "Detective", 480, 1, 2018);

        authorService.saveAuthor(firstAuthor);
        authorService.saveAuthor(secondAuthor);
        authorService.saveAuthor(thirdAuthor);

        firstBook.setAuthor(firstAuthor);
        secondBook.setAuthor(firstAuthor);
        thirdBook.setAuthor(secondAuthor);
        fourthBook.setAuthor(thirdAuthor);
        fifthBook.setAuthor(thirdAuthor);

        firstAuthor.addBook(firstBook);
        firstAuthor.addBook(secondBook);
        secondAuthor.addBook(thirdBook);
        thirdAuthor.addBook(fourthBook);
        thirdAuthor.addBook(fifthBook);

        bookService.saveBook(firstBook);
        bookService.saveBook(secondBook);
        bookService.saveBook(thirdBook);
        bookService.saveBook(fourthBook);
        bookService.saveBook(fifthBook);

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
