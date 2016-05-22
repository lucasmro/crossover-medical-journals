package com.crossover.medical.journals.auth;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;

import com.crossover.medical.journals.core.User;
import com.crossover.medical.journals.dao.UserDAO;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.hash.Hashing;

public class AuthenticationManager {

    private final UserDAO userDAO;

    public AuthenticationManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> findOneByToken(String token) {
        return userDAO.findOneByToken(token);
    }

    public User findOneByCredentials(String email, String password) {
        final Optional<User> user = userDAO.findOneByEmail(email);

        if (!user.isPresent() || isInvalidPassword(password, user.get())) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        return user.get();
    }

    public User createUserAndGenerateToken(User user) {
        if (userDAO.findOneByEmail(user.getEmail()).isPresent()) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }

        encodePasswordAndGenerateToken(user);

        return userDAO.save(user);
    }

    public User updateUserAndEncodePasswordAndGenerateNewToken(User user) {
        encodePasswordAndGenerateToken(user);

        return userDAO.save(user);
    }

    private boolean isInvalidPassword(String password, final User user) {
        return !BCrypt.checkpw(password, user.getPassword());
    }

    private void encodePasswordAndGenerateToken(User user) {
        final String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        final String token = generateToken(hashedPassword);

        user.setPassword(hashedPassword);
        user.setToken(token);
    }

    private String generateToken(String hashedPassword) {
        return Hashing.sha1().hashString(hashedPassword, Charsets.UTF_8).toString();
    }
}
