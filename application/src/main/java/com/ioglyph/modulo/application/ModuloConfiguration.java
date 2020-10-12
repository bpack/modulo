package com.ioglyph.modulo.application;

import com.ioglyph.modulo.core.user.UserLocationService;
import com.ioglyph.modulo.core.user.UserRepository;
import com.ioglyph.modulo.core.user.UserService;
import com.ioglyph.modulo.downstream.factory.GeolocationServiceFactory;
import com.ioglyph.modulo.persistence.user.JpaUserRepository;
import com.ioglyph.modulo.rest.ModuloRestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

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
    public UserLocationService locationService(@Autowired UserRepository repository,
            @Value("${modulo.freegeoip.url}") String url){
        logger.info("Initializing location services with url {}", url);
        return GeolocationServiceFactory.createUserLocationService(url, repository);
    }

    @Bean
    public ModuloRestApi userApi(@Autowired UserService service,
            @Autowired UserLocationService locator,
            @Autowired UserRepository repository){
        logger.info("Initializing API module");
        return new ModuloRestApi(service, locator, repository);
    }
}
