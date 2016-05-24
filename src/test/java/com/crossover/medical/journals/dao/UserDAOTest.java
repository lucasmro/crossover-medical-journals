package com.crossover.medical.journals.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.crossover.medical.journals.core.User;
import com.google.common.base.Optional;

public class UserDAOTest {

    private UserDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = mock(UserDAO.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindOneByEmailShouldReturnValidUser() {
        final User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setEmail("lucasmro@gmail.com");

        when(dao.findOneByEmail("lucasmro@gmail.com")).thenReturn(Optional.of(expectedUser));

        final Optional<User> actual = dao.findOneByEmail("lucasmro@gmail.com");

        assertThat(actual, equalTo(Optional.of(expectedUser)));
    }

    @Test
    public void testFindOneByEmailShoutReturnNull() {
        when(dao.findOneByEmail("inexistent@gmail.com")).thenReturn(Optional.absent());

        final Optional<User> actual = dao.findOneByEmail("inexistent@gmail.com");

        assertThat(actual, equalTo(Optional.absent()));
    }
}
