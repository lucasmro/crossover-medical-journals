package com.crossover.medical.journals.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        this.user = new User();
    }

    @Test
    public void testShouldCreateValidUser() {
        user.setId(1L);
        user.setName("Lucas Michelini Reis de Oliveira");
        user.setEmail("lucasmro@gmail.com");
        user.setPassword("123456");
        user.setRole(UserRole.SUBSCRIBER);
        user.setToken("token123");

        Assert.assertEquals(1L, user.getId(), 0);
        Assert.assertEquals("Lucas Michelini Reis de Oliveira", user.getName());
        Assert.assertEquals("lucasmro@gmail.com", user.getEmail());
        Assert.assertEquals(UserRole.SUBSCRIBER, user.getRole());
        Assert.assertEquals("token123", user.getToken());
    }
}
