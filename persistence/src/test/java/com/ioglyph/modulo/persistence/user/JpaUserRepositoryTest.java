package com.ioglyph.modulo.persistence.user;

import com.ioglyph.modulo.core.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@EnableAutoConfiguration
@DataJpaTest
public class JpaUserRepositoryTest {

    @Configuration
    static class TestConfig {
        @Bean
        public JpaUserRepository repository(@Autowired EntityManager em){
            return new JpaUserRepository(em);
        }
    }

    @Autowired
    JpaUserRepository repository;

    @Autowired
    EntityManager em;

    int counter = 0;

    @Test
    public void testContext(){
        Assertions.assertNotNull(em);
        Assertions.assertNotNull(repository);
    }

    @Test
    public void testSimplePersistLoad(){
        UUID id = insertUser();
        Assertions.assertNotNull(repository.load(id).get());
    }

    @Test
    public void testUpdate(){
        UUID id = insertUser();

        User u = repository.load(id).get();

        User user = new User(id, u.username().value(), "updated@example.com", "1.1.1.1", u.created(), u.updated(), true, null);
        repository.persist(user);
    }

    @Test
    public void testAll(){
        UUID first = insertUser();
        UUID second = insertUser();

        List<User> users = repository.all();

        Assertions.assertEquals(2, users.size());
    }

    private UUID insertUser(){
        UUID id = UUID.randomUUID();
        String username = "username" + Integer.toString(counter++);
        repository.persist(new User(id, username, username + "@example.com", "50.155.140.127", null, null, true, null));
        return id;
    }
}