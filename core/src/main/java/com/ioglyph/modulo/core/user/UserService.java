package com.ioglyph.modulo.core.user;

import java.util.UUID;

/**
 * Service interactor that handles User domain logic.
 */
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    /**
     * Handles invocation of the CreateUserCommand. Throws an exception if the
     * given username already exists.
     */
    public User create(CreateUserCommand command){
        User user = command.execute();
        if(repository.queryByUsername(user.username().value()).isEmpty()) {
            repository.persist(user);
            return user;
        }
        throw new IllegalArgumentException("Username " + user.username().value() + " already exists.");
    }

    public User update(UpdateUserCommand command){
        User user = command.execute();
        repository.persist(user);
        return user;
    }

    /**
     * Handles deletion of the User with the given ID or throws an exception if
     * no user exists with that ID.
     */
    public void delete(UUID id){
        User user = repository.load(id).orElseThrow(() -> new RuntimeException("No user with ID " + id));
        user.delete();
        repository.persist(user);
    }
}
