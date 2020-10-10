package com.ioglyph.modulo.application;

import com.ioglyph.modulo.core.user.User;
import com.ioglyph.modulo.core.user.UserRepository;
import com.ioglyph.modulo.core.user.UserService;
import com.ioglyph.modulo.persistence.user.JpaUserRepository;
import com.ioglyph.modulo.rest.ModuloRestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.UUID;

@Configuration
@EntityScan(basePackages = {"com.ioglyph.modulo.persistence.user"})
@ComponentScan(basePackages = {"com.ioglyph.modulo.security"})
public class ModuloConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ModuloConfiguration.class);

    @Bean
    public UserRepository userRepository(@Autowired EntityManager em){
        logger.info("Initializing persistence module");
        return new JpaUserRepository(em);
    }

    @Bean
    public UserService userService(@Autowired UserRepository repository){
        return new UserService(repository);
    }

    @Bean
    public ModuloRestApi userApi(@Autowired UserService service, @Autowired UserRepository repository){
        logger.info("Initializing API module");
        return new ModuloRestApi(service, repository);
    }

    public class DemoData {

        private final UserRepository repo;

        public DemoData(@Autowired UserRepository repo){
            this.repo = repo;
        }

        @EventListener
        public void appReady(ApplicationReadyEvent event) {

            User user = new User(UUID.randomUUID(),
                    "test.user",
                    "test_user@example.com");

            logger.info("Creating user with id = " + user.id());
            repo.persist(user);
        }
    }
}
