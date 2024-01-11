import dao.impl.*;
import model.*;
import service.*;
import org.hibernate.SessionFactory;
import utils.HibernateSessionFactoryUtil;
import java.util.List;

public class WebJspApplication {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        BookService bookService = new BookService(new BookDaoImpl(sessionFactory));
        AuthorService authorService = new AuthorService(new AuthorDaoImpl(sessionFactory));
        PublisherService publisherService = new PublisherService(new PublisherDaoImpl(sessionFactory));

        Author firstAuthor = new Author("Mary Stewart", "01.03.1933");
        Author secondAuthor = new Author("Sarah Gio", "30.04.1977");
        Author thirdAuthor = new Author("Rachel Caine", "26.10.1960");

        Publisher firstPublisher = new Publisher("Styria Pichler Verlag", "Austria");
        Publisher secondPublisher = new Publisher("Muenshen Edition Manufaktur", "German");
        Publisher thirdPublisher = new Publisher("Evro Book", "Serbia");

        Book firstBook = new Book("A fire in the night", "Detective", 322, 1990);
        Book secondBook = new Book("Abode of thorns", "Romance", 536, 1989);
        Book thirdBook = new Book("The last camellia", "Adventures", 298, 2004);
        Book fourthBook = new Book("Wolf River", "Detective", 450, 2013);
        Book fifthBook = new Book("Gloomy Bay", "Detective", 480, 2018);

        authorService.saveAuthor(firstAuthor);
        authorService.saveAuthor(secondAuthor);
        authorService.saveAuthor(thirdAuthor);

        publisherService.savePublisher(firstPublisher);
        publisherService.savePublisher(secondPublisher);
        publisherService.savePublisher(thirdPublisher);

        firstBook.setAuthor(firstAuthor);
        secondBook.setAuthor(firstAuthor);
        thirdBook.setAuthor(secondAuthor);
        fourthBook.setAuthor(thirdAuthor);
        fifthBook.setAuthor(thirdAuthor);

        firstBook.setPublisher(firstPublisher);
        secondBook.setPublisher(secondPublisher);
        thirdBook.setPublisher(firstPublisher);
        fourthBook.setPublisher(thirdPublisher);
        fifthBook.setPublisher(thirdPublisher);

        firstAuthor.addBook(firstBook);
        firstAuthor.addBook(secondBook);
        secondAuthor.addBook(thirdBook);
        thirdAuthor.addBook(fourthBook);
        thirdAuthor.addBook(fifthBook);

        firstPublisher.addBook(firstBook);
        firstPublisher.addBook(thirdBook);
        secondPublisher.addBook(secondBook);
        thirdPublisher.addBook(fourthBook);
        thirdPublisher.addBook(fifthBook);

        bookService.saveBook(firstBook);
        bookService.saveBook(secondBook);
        bookService.saveBook(thirdBook);
        bookService.saveBook(fourthBook);
        bookService.saveBook(fifthBook);
    }
}
