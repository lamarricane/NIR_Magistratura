package service;

import dao.impl.PublisherDaoImpl;
import model.Publisher;

import java.util.List;

public class PublisherService {
    private final PublisherDaoImpl publisherDao;

    public PublisherService(PublisherDaoImpl publisherDao) {
        this.publisherDao = publisherDao;
    }

    public Publisher findPublisherById(int id) {
        return publisherDao.findById(id);
    }

    public void savePublisher(Publisher publisher) {
        publisherDao.save(publisher);
    }

    public void updatePublisher(Publisher publisher) {
        publisherDao.update(publisher);
    }

    public void deletePublisher(Publisher publisher) {
        publisherDao.delete(publisher);
    }

    public List<Publisher> findAllPublishers() {
        return publisherDao.findAll();
    }
}
