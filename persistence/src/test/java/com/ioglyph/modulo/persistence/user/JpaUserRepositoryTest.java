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

    @Test
    public void testContext(){
        Assertions.assertNotNull(em);
        Assertions.assertNotNull(repository);
    }

    @Test
    public void testSimplePersistLoad(){
        UUID id = insertUser();
        Assertions.assertNotNull(repository.load(id));
    }

    private UUID insertUser(){
        UUID id = UUID.randomUUID();
        repository.persist(new User(id, "username1", "username1@example.com", "50.155.140.127", null, null, true, null));
        return id;
    }
}