package com.example.service;

import com.example.dao.ThesisDao;
import com.example.dao.ThesisDaoImpl;
import com.example.model.Thesis;
import org.hibernate.SessionFactory;

import java.util.List;

public class ThesisService {

    private final ThesisDao thesisDao;

    public ThesisService(SessionFactory sessionFactory) {
        this.thesisDao = new ThesisDaoImpl(sessionFactory);
    }

    public Thesis getThesisByGradeBook(String gradeBook) {
        return thesisDao.getByGradeBook(gradeBook);
    }

    public List<Thesis> getAllTheses() {
        return thesisDao.getAll();
    }

    public void addThesis(Thesis thesis) {
        thesisDao.save(thesis);
    }

    public void updateThesis(Thesis thesis) {
        thesisDao.update(thesis);
    }

    public void deleteThesis(String gradeBook) {
        thesisDao.delete(gradeBook);
    }

    public List<Thesis> searchTheses(String query) {
        return thesisDao.search(query);
    }
}
