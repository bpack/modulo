package com.ioglyph.modulo.core.user;

import java.util.UUID;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User create(CreateUserCommand command){
        User user = command.execute();
        repository.persist(user);
        return user;
    }

    public void delete(UUID id){
        User user = repository.load(id).orElseThrow(() -> new RuntimeException("No user with ID " + id));
        user.delete();
        repository.persist(user);
    }
}
