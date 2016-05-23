package com.crossover.medical.journals.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.crossover.medical.journals.core.Journal;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;

public class JournalDAO extends AbstractDAO<Journal> {

    public JournalDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Journal> findOneById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Journal save(Journal journal) {
        return persist(journal);
    }

    public List<Journal> findAll() {
        return list(namedQuery("Journal.findAll"));
    }

    public void delete(Journal journal) {
        currentSession().delete(journal);
    }
}
