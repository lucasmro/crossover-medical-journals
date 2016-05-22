package com.crossover.medical.journals.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.crossover.medical.journals.core.User;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<User> findOneById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Optional<User> findOneByEmail(String email) {
        return Optional.fromNullable(uniqueResult(namedQuery("User.findOneByEmail").setString("email", email)));
    }

    public Optional<User> findOneByToken(String token) {
        return Optional.fromNullable(uniqueResult(namedQuery("User.findOneByToken").setString("token", token)));
    }

    public User save(User user) {
        return persist(user);
    }

    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }

    public void delete(User user) {
        currentSession().delete(user);
    }
}
