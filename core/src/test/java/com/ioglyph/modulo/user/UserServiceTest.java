package com.ioglyph.modulo.user;

import com.ioglyph.modulo.core.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceTest {

    @Test
    public void testCreate(){
        UserService service = new UserService(new RepositoryFake());
        CreateUserCommand command = new CreateUserCommand("username", "user@example.com", "1.1.1.1");
        User user = service.create(command);
        Assertions.assertNotNull(user);
    }

    @Test
    public void testUpdate(){
        User user = new User(UUID.randomUUID(), "user", "user@example.com", "1.1.1.1", null, null, true, null);
        UserService service = new UserService(new RepositoryFake());
        UpdateUserCommand command = new UpdateUserCommand(user, "new_email@email.com", "2.2.2.2");
        User updated = service.update(command);
        Assertions.assertNotNull(updated);
    }

    private class RepositoryFake implements UserRepository{

        @Override
        public void persist(User user) {

        }

        @Override
        public Optional<User> load(UUID id) {
            return Optional.empty();
        }

        @Override
        public List<User> all() {
            return null;
        }
    }
}
